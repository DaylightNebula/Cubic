# Cubic

## Prompt
Im a terrible mod developer but Im good at writing server plugins.  How could I allow plugins to be used in "singleplayer" worlds?

## Goals
### Primary
Allow players to use plugins in their "singleplayer" worlds.  To do this, we create a Minecraft servers and running them in the background.

### Custom Games
This can also be used with systems like [Meld](https://github.com/DaylightNebula/Meld.git) and [Valence](https://github.com/valence-rs/valence) to create completely custom games in singleplayer.

## Server Platforms

| Platform | Support Status |
|----------|----------------|
| Vanilla  | In Progress    |
| Paper    | In Progress    |
| Valence  | Planned        |
| Meld     | Planned        |
| Sponge   | Planned        |

## Future Posibilities
### Custom Resource API
Create libraries for all supported server platforms (expect maybe for Vanilla) that allow for custom models to be loaded and animated.  We could load from BBModel files or Minecraft model files and create the resources needed to use those models in game.  These custom models would be useful for creating custom blocks, items, and entities.