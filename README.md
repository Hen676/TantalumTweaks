<!--suppress HtmlDeprecatedAttribute -->
<p align="center">
  <img src="https://github.com/Hen676/TantalumTweaks/blob/master/src/main/resources/assets/tantalumtweaks/icon.png"  alt="Logo"/>
</p>

# TantalumTweaks

Client-side Minecraft fabric mod with minor quality-of-life features.

## Features

### Keybinds
| Name         | Description                                                                                                                       | Default Bind |
|--------------|-----------------------------------------------------------------------------------------------------------------------------------|--------------|
| Zoom         | Zooms in the player's camera.                                                                                                     | `V`          |
| Freecam      | User has free control of their camera.                                                                                            | `F4`         |
| Light Level  | Renders squares on blocks around the player that are light-level 0.                                                               | `Numpad1`    |
| Mob Health   | Renders percentage of the entities' health above their head.                                                                      | `Numpad2`    |
| Full Bright  | Causes the world to appear unlit (Does not work with shaders).                                                                    | `Numpad3`    |
| Auto Attack  | Sets the button for Attack (Default key is LMB) to true. Can be disabled by using the Attack or Auto Attack key bind.             | `Numpad4`    |
| Auto Use     | Sets the button for Use (Default key is RMB) to true. Can be disabled by using the Use or Auto Use key bind.                      | `Numpad5`    |
| Auto Forward | Sets the button for Forward (Default key is W) to true. Can be disabled by using the Forward or Auto Forward key bind.            | `Numpad6`    |
| Debug        | Only shows up in Minecraft development mode. For debugging and logging various parts of the mod.                                  | `Numpad0`    |

### Commands
- Entity List command
  - Give a list of the number of entities around the player in an N radius (The default radius is 10 blocks).

### Tooltips
- Durability tooltips
  - Adds durability to tooltips. This includes effective durability due to unbraking and effective elytra flight time.
- Food tooltips
  - Adds Hunger and Saturation of given food to the tooltip.
- Fuel tooltips
  - Adds item's burn time and items it can smelt to the tooltip.
- Compost tooltips
  - Adds a chance of the item giving a compost layer.
- Effect tooltips
  - Adds more `When Applied:` effects for potion effects.

### Misc
- Remove Fog
  - Cancels fog rendering (Not compatible with shaders).
- Compass
  - Renders an X, Y, Z, and Facing direction to the user's HUD.
- Smokey Furances
  - Makes Furnaces, Smokers, and Blast Furnaces produce smoke.
- Resource Pack
  - Makes shields render out of view in first person
  - Reduces the on-screen effect of wearing a pumpkin

## Config 
Config is available via a button in the options menu. Config is also available via [Mod Menu](https://modrinth.com/mod/modmenu) if installed.

## License
[MIT](https://github.com/Hen676/TantalumTweaks/blob/master/LICENSE)
