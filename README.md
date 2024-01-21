# shardingsphere-graalvm-v2311-native-test
# Fixed by https://github.com/apache/shardingsphere/pull/29764 .

- For https://github.com/oracle/graal/issues/7500.
- Also for https://github.com/apache/shardingsphere/pull/28609 and https://github.com/apache/shardingsphere/issues/29052 .
- On the new Ubuntu 22.04.3 instance, execute the following command:
```bash
sudo apt install unzip zip curl sed -y
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21.0.1-graalce
sdk use java 21.0.1-graalce

git clone git@github.com:apache/shardingsphere.git
cd ./shardingsphere/
git reset --hard 5575f8bf0764d84693304e498bc5806b25dcd7e0
./mvnw clean install -Prelease -T1C -DskipTests -Djacoco.skip=true -Dcheckstyle.skip=true -Drat.skip=true -Dmaven.javadoc.skip=true

cd ../

git clone git@github.com:linghengqian/shardingsphere-graalvm-v2311-native-test.git
cd ./shardingsphere-graalvm-v2311-native-test/
./gradlew -Pagent clean test
./gradlew metadataCopy --task test
./gradlew clean nativeTest
```

- You'll notice a javac-related Error Log.

```bash
$ ./gradlew clean nativeTest
Starting a Gradle Daemon, 1 incompatible and 2 stopped Daemons could not be reused, use --status for details

> Task :generateTestResourcesConfigFile
[native-image-plugin] Resources configuration written into /home/linghengqian/TwinklingLiftWorks/git/public/shardingsphere-graalvm-v2311-native-test/build/native/generated/generateTestResourcesConfigFile/resource-config.json

> Task :nativeTestCompile
[native-image-plugin] GraalVM Toolchain detection is disabled
[native-image-plugin] GraalVM location read from environment variable: JAVA_HOME
[native-image-plugin] Native Image executable path: /home/linghengqian/.sdkman/candidates/java/21.0.1-graalce/lib/svm/bin/native-image
========================================================================================================================
GraalVM Native Image: Generating 'shardingsphere-graalvm-v2311-native-test-tests' (executable)...
========================================================================================================================
For detailed information and explanations on the build output, visit:
https://github.com/oracle/graal/blob/master/docs/reference-manual/native-image/BuildOutput.md
------------------------------------------------------------------------------------------------------------------------
[1/8] Initializing...                                                                                   (16.7s @ 0.29GB)
 Java version: 21.0.1+12, vendor version: GraalVM CE 21.0.1+12.1
 Graal compiler: optimization level: 2, target machine: x86-64-v3
 C compiler: gcc (linux, x86_64, 11.4.0)
 Garbage collector: Serial GC (max heap size: 80% of RAM)
 3 user-specific feature(s):
 - com.oracle.svm.polyglot.groovy.GroovyIndyInterfaceFeature
 - com.oracle.svm.thirdparty.gson.GsonFeature
 - org.graalvm.junit.platform.JUnitPlatformFeature
------------------------------------------------------------------------------------------------------------------------
Build resources:
 - 9.57GB of memory (75.6% of 12.67GB system memory, determined at start)
 - 6 thread(s) (100.0% of 6 available processor(s), determined at start)
[junit-platform-native] Running in 'test listener' mode using files matching pattern [junit-platform-unique-ids*] found in folder [/home/linghengqian/TwinklingLiftWorks/git/public/shardingsphere-graalvm-v2311-native-test/build/test-results/test/testlist] and its subfolders.
[2/8] Performing analysis...  []                                                                       (102.5s @ 3.64GB)
   25,844 reachable types   (78.0% of   33,132 total)
   40,751 reachable fields  (51.7% of   78,856 total)
  126,284 reachable methods (55.0% of  229,632 total)
    7,372 types,   786 fields, and 6,920 methods registered for reflection

------------------------------------------------------------------------------------------------------------------------
Error: java.util.concurrent.ExecutionException: java.lang.ClassCastException: class jdk.vm.ci.meta.NullConstant cannot be cast to class org.graalvm.compiler.core.common.type.TypedConstant (jdk.vm.ci.meta.NullConstant is in module jdk.internal.vm.ci of loader 'bootstrap'; org.graalvm.compiler.core.common.type.TypedConstant is in module jdk.internal.vm.compiler of loader 'platform')
                       18.5s (15.2% of total time) in 54 GCs | Peak RSS: 5.67GB | CPU load: 5.09
========================================================================================================================
Finished generating 'shardingsphere-graalvm-v2311-native-test-tests' in 2m 0s.

> Task :nativeTestCompile FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':nativeTestCompile'.
> Process 'command '/home/linghengqian/.sdkman/candidates/java/21.0.1-graalce/bin/native-image'' finished with non-zero exit value 1

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

BUILD FAILED in 2m 23s
7 actionable tasks: 7 executed

```
