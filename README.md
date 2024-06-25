# PluginSimplifier
Simplify plugin development using this Minecraft plugin library and save time.


## How to use it in your own project?

Gradle:
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.max1mde:PluginSimplifier:1.0.0'
}
```

**Maven installation**
```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>

<dependency>
  <groupId>com.github.max1mde</groupId>
  <artifactId>PluginSimplifier</artifactId>
  <version>1.0.0</version>
</dependency>
```

# Concept`s

- All classes which implement bukkit Listener
  are automatically registered

- All classes which implement the CommandExecuter
    are automatically registering a command if there is a register command annotation


Example
```
@Command(name="example", description="asd", aliases="", permission="")
public class ExampleCommand ... {

}

```

Don't forget to shade the library
