# CxvqoIgnore

A client-side Fabric mod that ignores (hides) player and system chat messages matching the configured patterns.

_yes this readme is vibecoded since im not gonna bother to write one_
_and the ai hallucinated that there was 1.21.11 support, there was never_

**One jar works on Minecraft 26.2.** The mod is compiled against the version-stable Fabric API surface shared by both releases and shipped in Fabric's version-stable intermediary mappings, so a single build covers both versions.

By default the mod is enabled. Toggle it with:

```
/cxvqoignore enable
/cxvqoignore disable
```

## What it filters

When enabled, any player or system chat message matching any of the configured regex patterns (case-insensitive) is silently ignored.

The default patterns are:

- `/.*cxvqo.*/i`
- `/.*denisapain.*/i`

## Regex pattern management

Patterns can be added, removed, and listed at runtime. Each pattern is matched against the whole message, so include `.*` on both sides to ignore messages that merely contain text. Added patterns are compiled case-insensitively.

```
/cxvqoignore addregex <regex>
/cxvqoignore removeregex <index>
/cxvqoignore listregex
```

- `addregex <regex>` — adds a new pattern, e.g. `/cxvqoignore addregex .*skiddie.*`. The regex must be valid Java regex syntax, or the mod reports an error.
- `removeregex <index>` — removes the pattern at the given index (see the indexes shown by `listregex`).
- `listregex` — lists every configured pattern, each prefixed with its index for use with `removeregex`.

Example — ignore everyone whose name starts with "Foo":

```
/cxvqoignore addregex .*Foo.*
/cxvqoignore listregex
/cxvqoignore removeregex 2
```

## Requirements

- Minecraft: Java Edition **26.2**
- Fabric Loader: 0.19.3
- Fabric API: any version for 26.2+ (uses the shared `fabric-message-api-v1` / `fabric-command-api-v2` surface)
- Java: **25** or newer

## Building

```
./gradlew build
```

The built jar will be in `build/libs/`. Building requires a JDK 25+ toolchain (used to target Minecraft 26.2).

## License

Apache License 2.0 — see [LICENSE](LICENSE).
