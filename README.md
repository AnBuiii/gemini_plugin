# Gemini Plugin

![Build](https://github.com/AnBuiii/intellij_plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/24010-gemini)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/24010-gemini)

![new gemini 2](https://github.com/AnBuiii/gemini_plugin/assets/89350086/8e43d9ab-5f8c-4132-ae22-97983505862f)

<!-- Plugin description -->

## Description

Gemini is Intellij Platform's plugin that auto generate answer in code editor backed by Google's Gemini

## How it works?

Simply get the answer from [Google's Gemini API](https://gemini.google.com/app) to explain your Kotlin or Java code, and print it in [comments block](https://kotlinlang.org/docs/basic-syntax.html#comments).

## How to use?

- Get the **API key**: https://aistudio.google.com/app/apikey
- Save it somewhere because you never see it again
- Install plugin:
   - Use IDE built-in plugin system: <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Gemini"</kbd> >
     <kbd>Install</kbd>
   - Or download [latest release](https://github.com/AnBuiii/intellij_plugin/releases/latest) (the signed .zip) and manually install: <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>
- Enable the plugin: <kbd>Settings</kbd> > <kbd>Tools</kbd> > <kbd>Gemini</kbd> > Tick <kbd>Enable Gemini?</kbd> > Insert your **API Key** > <kbd>Apply</kbd>
- Select anything on editor > Right click > <kbd>Explain this!</kbd> > Get the answer
- 
<!-- Plugin description end -->

## Todo
- [ ] Add some shortcut 
- [ ] Add tool window for asked questions and answers
- [ ] Maybe another LLM (ChatGPT, Lllama 2,...)

## Contribute
If you want to contribute to this library, you're always welcome!

Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template

[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation


