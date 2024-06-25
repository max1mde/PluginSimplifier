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

Don't forget to shade the library
