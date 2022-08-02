# signal

Signal is an API for adding redstone-like blocks.

## Information for mod developers

While the Signal API does not add any tools for adding custom blocks in general, it abstracts the concept of redstone power and handles the interactions between different signal types.

If you wish to use the signal API for your own custom redstone-like blocks, you can add it as a dependency to your project.

- Download the desired release of the Signal API from [github](https://github.com/SpaceWalkerRS/signal/releases), [modrinth](https://github.com/SpaceWalkerRS/signal/releases), or [curseforge](https://github.com/SpaceWalkerRS/signal/releases), and place it in `/vendor/signal/` in your project folder.

- In `build.gradle` add the following to the `repositories { }` section:

```
repositories {
	...
	flatDir {
		dirs "./vendor/signal/"
	}
}
```

- In `build.gradle` add the following to the `dependencies { }` section:

```
dependencies {
	...
	modImplementation ":signal:${project.signal_version}"
}
```

- Define the version of Signal you are using in `gradle.properties`, for example:

```
signal_version=mc1.19-1.0.0
```

- If you wish to create your own custom signal and/or wire types, you must define an entrypoint in your `fabric.mod.json`:

```
	"entrypoints": {
		...
		"signal": [
			"com.example.ExampleInitializer"
		]
	}
```

- Create an initializer to register your custom signal and/or wire types:

```java
package com.example;

import signal.api.SignalInitializer;

public class ExampleInitializer implements SignalInitializer {

	@Override
	public void onInitializeSignal() {
		// register your custom signal and/or wire types here
	}
}
```

- Custom signal source blocks should implement the `SignalSource` interface. This interface declares methods that define how/when/where your block emits a signal.

```java
...

import signal.api.signal.block.SignalSource;

public class MyCoolSignalSource extends Block implements SignalSource {
	...
}
```

- Custom analog signal source blocks should implement the `AnalogSignalSource` interface.

```java
...

import signal.api.signal.block.AnalogSignalSource;

public class MyCoolSignalSource extends Block implements AnalogSignalSource {
	...
}
```

- Custom signal consumer blocks should implement the `SignalConsumer` interface. This interface has utility methods for determining the signal that your block is receiving from neighboring blocks.

```java
...

import signal.api.signal.block.SignalConsumer;

public class MyCoolSignalSource extends Block implements SignalConsumer {
	...
}
```

- Custom wire blocks should implement the `Wire` interface. This interface declares methods that define how your wire block interacts with neighboring (non-wire) blocks. Your custom wire type will define how your wire block interacts with other wires.

```java
...

import signal.api.signal.wire.block.Wire;

public class MyCoolSignalSource extends Block implements Wire {
	...
}
```
