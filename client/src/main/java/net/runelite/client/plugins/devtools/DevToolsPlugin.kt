package net.runelite.client.plugins.devtools

import eventbus.events.ConfigChanged
import eventbus.events.GameStateChanged
import meteor.dev.widgetinspector.WidgetInspector
import meteor.plugins.Plugin
import meteor.plugins.PluginDescriptor
import meteor.rs.ClientThread
import net.runelite.api.GameState

@PluginDescriptor(name = "Dev Tools", enabledByDefault = false, description = "")
class DevToolsPlugin : Plugin() {
    // Gets config from ConfigManager, and returns the proper type for accessing
    var config = configuration<DevToolsConfig>()

    // Adds passed overlay to list, which is added/removed on plugin start/stop. Can be called repetitively
    var overlay = overlay(DevToolsOverlay(this))

    val inspector by lazy { EventInspector(client, ClientThread, ProjectileTracker(client)) }


    override fun onGameStateChanged(it: GameStateChanged) {
        super.onGameStateChanged(it)

        if (it.gameState == GameState.LOADING || it.gameState != GameState.LOGGED_IN) {
            return
        }

        if (config.inspectorActive()) {
            inspector.open()
        } else {
            inspector.close()
        }
    }

    override fun onConfigChanged(it: ConfigChanged) {
        if (client.gameState.state < GameState.LOGIN_SCREEN.state) {
            return
        }

        if (it.key == "eventInspectorActive" && config.inspectorActive()) {
            inspector.open()
        } else {
            inspector.close()
        }

        if(it.key == "widgetInspectorActive" && config.widgetInspectorActive())
        {
            WidgetInspector.open()
        }else{
            WidgetInspector.close()
        }
    }
}