# signal

Signal is an API for adding redstone-like blocks.

## Information for mod developers

While the Signal API does not add any tools for adding custom blocks in general, it abstracts the concept of redstone power and handles the interactions between different signal types.

If you wish to use the signal API for your own custom redstone-like blocks, you can add it as a dependency to your project.

- In `build.gradle` add the following to the `repositories { }` section:
```
repositories {
	...
	maven {
		url "https://api.modrinth.com/maven"
		content {
			includeGroup "maven.modrinth"
		}
	}
}
```

- In `build.gradle` add the following to the `dependencies { }` section:

```
dependencies {
	...
	modImplementation "maven.modrinth:signal:${project.signal_version}"
}
```

- Define the version of Signal you are using in `gradle.properties`, for example:

```
signal_version=mc1.19-1.0.0
```

- If you wish to create your own custom signal and/or wire types, you should bootstrap them in your mod's initializer. Custom signal types can be registered through [`SignalTypes#register`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/SignalTypes.java#L22) while custom wire types can be registered through [`WireTypes#register`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/wire/WireTypes.java#L23).

- A block's signal behavior is controlled by implementing methods from the [`SignalBlockBehavior`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/SignalBlockBehavior.java#L14) interface. Signal source blocks should implement the [`isSignalSource`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/SignalBlockBehavior.java#L16) method, analog signal source blocks should implement the [`isAnalogSignalSource`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/SignalBlockBehavior.java#L36) method, signal consumer blocks should implement the [`isSignalConsumer`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/SignalBlockBehavior.java#L44) method, custom signal conductors should implement the [`isSignalConductor`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/SignalBlockBehavior.java#L48) method (by default vanilla redstone conductors will conduct signals of any type), and custom wire blocks should implement the [`isWire`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/SignalBlockBehavior.java#L52) method.

- Signal type equality should be checked through the [`SignalType#is`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/SignalType.java#L41) method, this is to allow the use of [`SignalTypes.ANY`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/SignalTypes.java#L11) to capture any and all signal types.

- Wire type equality should be checked through the [`WireType#is`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/wire/WireType.java#L72) method, this is to allow the use of [`WireTypes.ANY`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/wire/WireTypes.java#L12) to capture any and all wire types.

- If your signal source block only emits one type of signal, you can implement the [`BasicSignalSource`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/block/BasicSignalSource.java#L18) interface to offload the work of dealing with signal types. Similarly, the [`BasicAnalogSignalSource`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/block/BasicAnalogSignalSource.java#L20), [`BasicSignalConsumer`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/block/BasicSignalConsumer.java#L16), and [`BasicWire`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/signal/wire/block/BasicWire.java#L21) interfaces can be used for custom analog signal source blocks, signal consumer blocks, and wire blocks respectively.

- All of the vanilla methods for querying emitted and received power are deprecated, and the methods declared in [`SignalLevel`](https://github.com/SpaceWalkerRS/signal/blob/117cc1a4d16b6e5414a4b99eea53f65616da2575/src/main/java/signal/api/SignalLevel.java#L8) should be used instead. If your block implements the `BasicSignalConsumer` interface, you can call the helper methods it defines to offload the work of dealing with signal types.
