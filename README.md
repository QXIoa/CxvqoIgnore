# CxvqoIgnore

A client-side Fabric mod that ignores (hides) player and system chat messages matching the configured patterns.

_and yes this readme is vibecoded, i could not bother to write one manually lmao._
_oh and the ai hallucinated 1.21.11 support, there never was XD_
**One jar works on Minecraft 26.2.** The mod is compiled against the version-stable Fabric API surface shared by both releases and shipped in Fabric's version-stable intermediary mappings, so a single build covers both versions.

By default the mod is enabled. Toggle it with:

```
/cxvqoignore enable
/cxvqoignore disable
```

## What it filters

When enabled, any player or system chat message matching either of these regex patterns (case-insensitive) is silently ignored:

- `/.*cxvqo.*/i`
- `/.*denisapain.*/i`

## Requirements

- Minecraft: Java Edition **26.2**
- Fabric Loader: 0.19.3
- Fabric API: any version for 26.2+ (uses the shared `fabric-message-api-v1` / `fabric-command-api-v2` surface)
- Java: **21** or newer

## Building

```
./gradlew build
```

The built jar will be in `build/libs/`. Building requires a JDK 25+ toolchain (used to target Minecraft 26.2).

## License

Apache License 2.0 — see [LICENSE](LICENSE).
