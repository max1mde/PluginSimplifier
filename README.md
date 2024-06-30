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

# Concepts

- Commands:
    The registerCommands(String...) method should accept package names as arguments, such as "com/maximde/coolplugin". This method will search through the specified packages and their subpackages. If any class implements CommandExecutor and has the @Register 
    annotation, the command will be registered automatically.

- Listeners:
    The registerEvents(String...) method should accept package names as arguments, similar to registerCommands. This method will search through the specified packages and their subpackages. If any class implements Listener, it will be registered automatically.


Example
```
@Register(name="example", description="example command", aliases="ex, ex2", permission="example.use")
public boolean onCommand ... {

}

```
**You only need to add `name="<string>"`, everything else is optimal.**

Don't forget to shade the library
