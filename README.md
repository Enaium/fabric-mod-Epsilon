# Epsilon

## Plugin Example

Directory `./Epsilon/plugins`

### MainClass
```java
package cn.enaium.plugin;

import cn.enaium.epsilon.plugin.api.PluginInitializer;

public class Test implements PluginInitializer {

    @Override
    public void onInitialize() {
        System.out.println("HELLO WORLD!");
    }

}
```

### Resources `epsilon.plugin.json`

```json
{
  "name": "Test",
  "description": "Test Plugin",
  "authors": [
    "Enaium"
  ],
  "mainClass": "cn.enaium.plugin.Test"
}
```
