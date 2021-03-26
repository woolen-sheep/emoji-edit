# emoji-edit

[中文|zh-CN](https://github.com/woolen-sheep/emoji-edit/blob/main/README-ZH.md)

Emoji-edit is a module relies on Xposed-like framework, which can replace text with appropriate emoji symbols according to your input in any input box.

## Dependency

- Rooted Android phone with Magisk Manager
- Riru module
- LSPosed module

## Quick Start

- Download the apk in Release and install it
- Activate the emoji-edit module in the LSPosed manager
- Check the application that this module applies to in the module
- Restart the checked application
- Allow emoji-edit to start automatically in the application information, and lock emoji-edit in the background
- Enjoy it

## Compatibility

Currently, only a few systems have been tested, including

- [x] H<sub>2</sub>OS		API29
- [x] MIUI		API28

If emoji-edit runs normally on your system, you can tell us by making a comment under the discussion. 

If emoji-edit raises an exception on your system or in an application, please submit an issue with the LSPosed log before the error occurred.

## FAQ

Can I use other Xposed frameworks?

- Other Xposed frameworks (such as EdXposed, Tai Chi) can theoretically also take effect. But there is no scope limit, emoji-edit will take effect in all your applications, which may cause compatibility and stability issues.

Will this module slow down the system / increase power consumption?

- In the development process, this issue was considered as much as possible. At present, the impact of emoji-edit on the system and the power consumption are extremely low.

Why should emoji-edit stay in the background? What is the notification in the notification bar?

- In order to improve the running speed, emoji-edit starts a local query service, so there must be a service resident in the background. The notification in the notification bar is this service. If you allow self-starting after booting, when you restart for the first time, the emoji-edit app does not need to run in the background, only the service stays in the background.

Why can't I display some emoji?

- The emoji font library is incomplete on some Android systems. You can install [this Magisk module](https://github.com/Keinta15/Magisk-iOS-Emoji) to complete your font library.

