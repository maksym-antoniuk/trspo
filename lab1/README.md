## Median Filter

### Build

#### You should have Gradle and JDK installed

> To build project go to project directory, then execute in terminal

```
gradle jar
```

> You find executable jar file in build/libs with name `image-filtrator.jar` <br />
To run program go to dir with jar file then execute in terminal

```
java -jar image-filtrator.jar <filter> <-rM> [-tN] path_to_input_image [path_to_result_image]
```

* _value in [] are optional_
* _if absent `path_to_result_image` by default will be `outputyyyy-MM-dd-HH-mm-ss-SSS.jpg` in current directory_
* _flag `-tN` filter input image in `N` parallel threads_
* _`<filter>` now can be `gaussian` or `median`_
* _flag `-rM` set window radius `M`_