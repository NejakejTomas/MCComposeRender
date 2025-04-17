@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package cz.nejakejtomas.composescreen

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers
import org.lwjgl.glfw.GLFW

object Keys {

    fun getComposeKeyEvent(
        event: KeyEvent,
        keyCode: Int,
        scanCode: Int,
        modifiers: Int
    ): androidx.compose.ui.input.key.KeyEvent {
        return androidx.compose.ui.input.key.KeyEvent(
            androidx.compose.ui.input.key.InternalKeyEvent(
                key = Key(
                    nativeKeyCode = glfwToAwtKeyCode(keyCode),
                    nativeKeyLocation = java.awt.event.KeyEvent.KEY_LOCATION_STANDARD
                ),
                type = when (event) {
                    KeyEvent.Press -> KeyEventType.KeyDown
                    KeyEvent.Release -> KeyEventType.KeyUp
                },
                codePoint = '\uFFFF',
                modifiers = PointerKeyboardModifiers(
                    isCtrlPressed = Modifiers.isControlPressed(modifiers),
                    isAltPressed = Modifiers.isAltPressed(modifiers),
                    isShiftPressed = Modifiers.isShiftPressed(modifiers),
                    isCapsLockOn = Modifiers.isCapsPressed(modifiers),
                    isNumLockOn = Modifiers.isNumLockPressed(modifiers),
                )
            )
        )
    }

    fun getComposeTypeEvent(codePoint: Char, modifiers: Int): androidx.compose.ui.input.key.KeyEvent {
        return androidx.compose.ui.input.key.KeyEvent(
            androidx.compose.ui.input.key.InternalKeyEvent(
                key = Key(
                    nativeKeyCode = java.awt.event.KeyEvent.VK_UNDEFINED,
                    nativeKeyLocation = java.awt.event.KeyEvent.KEY_LOCATION_UNKNOWN
                ),
                type = KeyEventType.Unknown,
                codePoint = codePoint,
                modifiers = PointerKeyboardModifiers(
                    isCtrlPressed = Modifiers.isControlPressed(modifiers),
                    isAltPressed = Modifiers.isAltPressed(modifiers),
                    isShiftPressed = Modifiers.isShiftPressed(modifiers),
                    isCapsLockOn = Modifiers.isCapsPressed(modifiers),
                    isNumLockOn = Modifiers.isNumLockPressed(modifiers),
                ),
                nativeEvent = java.awt.event.KeyEvent(
                    dummyComponent,
                    java.awt.event.KeyEvent.KEY_TYPED,
                    System.currentTimeMillis(),
                    0,
                    java.awt.event.KeyEvent.VK_UNDEFINED,
                    codePoint
                )
            )
        )
    }

    enum class KeyEvent {
        Press,
        Release,
    }

    private val dummyComponent = object : java.awt.Component() {}

    private object Modifiers {
        fun isShiftPressed(modifier: Int) = modifier and GLFW.GLFW_MOD_SHIFT != 0
        fun isControlPressed(modifier: Int) = modifier and GLFW.GLFW_MOD_CONTROL != 0
        fun isAltPressed(modifier: Int) = modifier and GLFW.GLFW_MOD_ALT != 0
        fun isSuperPressed(modifier: Int) = modifier and GLFW.GLFW_MOD_SUPER != 0
        fun isCapsPressed(modifier: Int) = modifier and GLFW.GLFW_MOD_CAPS_LOCK != 0
        fun isNumLockPressed(modifier: Int) = modifier and GLFW.GLFW_MOD_NUM_LOCK != 0
    }

    private fun glfwToAwtKeyCode(keyCode: Int): Int {
        return glfwToAwtKeyMap.getOrDefault(keyCode, java.awt.event.KeyEvent.VK_UNDEFINED)
    }

    private val glfwToAwtKeyMap: Map<Int, Int> = mapOf(
        // Printable keys
        GLFW.GLFW_KEY_SPACE to java.awt.event.KeyEvent.VK_SPACE,
        GLFW.GLFW_KEY_APOSTROPHE to java.awt.event.KeyEvent.VK_QUOTE,
        GLFW.GLFW_KEY_COMMA to java.awt.event.KeyEvent.VK_COMMA,
        GLFW.GLFW_KEY_MINUS to java.awt.event.KeyEvent.VK_MINUS,
        GLFW.GLFW_KEY_PERIOD to java.awt.event.KeyEvent.VK_PERIOD,
        GLFW.GLFW_KEY_SLASH to java.awt.event.KeyEvent.VK_SLASH,
        GLFW.GLFW_KEY_0 to java.awt.event.KeyEvent.VK_0,
        GLFW.GLFW_KEY_1 to java.awt.event.KeyEvent.VK_1,
        GLFW.GLFW_KEY_2 to java.awt.event.KeyEvent.VK_2,
        GLFW.GLFW_KEY_3 to java.awt.event.KeyEvent.VK_3,
        GLFW.GLFW_KEY_4 to java.awt.event.KeyEvent.VK_4,
        GLFW.GLFW_KEY_5 to java.awt.event.KeyEvent.VK_5,
        GLFW.GLFW_KEY_6 to java.awt.event.KeyEvent.VK_6,
        GLFW.GLFW_KEY_7 to java.awt.event.KeyEvent.VK_7,
        GLFW.GLFW_KEY_8 to java.awt.event.KeyEvent.VK_8,
        GLFW.GLFW_KEY_9 to java.awt.event.KeyEvent.VK_9,
        GLFW.GLFW_KEY_SEMICOLON to java.awt.event.KeyEvent.VK_SEMICOLON,
        GLFW.GLFW_KEY_EQUAL to java.awt.event.KeyEvent.VK_EQUALS,
        GLFW.GLFW_KEY_A to java.awt.event.KeyEvent.VK_A,
        GLFW.GLFW_KEY_B to java.awt.event.KeyEvent.VK_B,
        GLFW.GLFW_KEY_C to java.awt.event.KeyEvent.VK_C,
        GLFW.GLFW_KEY_D to java.awt.event.KeyEvent.VK_D,
        GLFW.GLFW_KEY_E to java.awt.event.KeyEvent.VK_E,
        GLFW.GLFW_KEY_F to java.awt.event.KeyEvent.VK_F,
        GLFW.GLFW_KEY_G to java.awt.event.KeyEvent.VK_G,
        GLFW.GLFW_KEY_H to java.awt.event.KeyEvent.VK_H,
        GLFW.GLFW_KEY_I to java.awt.event.KeyEvent.VK_I,
        GLFW.GLFW_KEY_J to java.awt.event.KeyEvent.VK_J,
        GLFW.GLFW_KEY_K to java.awt.event.KeyEvent.VK_K,
        GLFW.GLFW_KEY_L to java.awt.event.KeyEvent.VK_L,
        GLFW.GLFW_KEY_M to java.awt.event.KeyEvent.VK_M,
        GLFW.GLFW_KEY_N to java.awt.event.KeyEvent.VK_N,
        GLFW.GLFW_KEY_O to java.awt.event.KeyEvent.VK_O,
        GLFW.GLFW_KEY_P to java.awt.event.KeyEvent.VK_P,
        GLFW.GLFW_KEY_Q to java.awt.event.KeyEvent.VK_Q,
        GLFW.GLFW_KEY_R to java.awt.event.KeyEvent.VK_R,
        GLFW.GLFW_KEY_S to java.awt.event.KeyEvent.VK_S,
        GLFW.GLFW_KEY_T to java.awt.event.KeyEvent.VK_T,
        GLFW.GLFW_KEY_U to java.awt.event.KeyEvent.VK_U,
        GLFW.GLFW_KEY_V to java.awt.event.KeyEvent.VK_V,
        GLFW.GLFW_KEY_W to java.awt.event.KeyEvent.VK_W,
        GLFW.GLFW_KEY_X to java.awt.event.KeyEvent.VK_X,
        GLFW.GLFW_KEY_Y to java.awt.event.KeyEvent.VK_Y,
        GLFW.GLFW_KEY_Z to java.awt.event.KeyEvent.VK_Z,
        GLFW.GLFW_KEY_LEFT_BRACKET to java.awt.event.KeyEvent.VK_OPEN_BRACKET,
        GLFW.GLFW_KEY_BACKSLASH to java.awt.event.KeyEvent.VK_BACK_SLASH,
        GLFW.GLFW_KEY_RIGHT_BRACKET to java.awt.event.KeyEvent.VK_CLOSE_BRACKET,
        GLFW.GLFW_KEY_GRAVE_ACCENT to java.awt.event.KeyEvent.VK_BACK_QUOTE,
        GLFW.GLFW_KEY_WORLD_1 to java.awt.event.KeyEvent.VK_UNDEFINED,
        GLFW.GLFW_KEY_WORLD_2 to java.awt.event.KeyEvent.VK_UNDEFINED,

        // Function & navigation keys
        GLFW.GLFW_KEY_ESCAPE to java.awt.event.KeyEvent.VK_ESCAPE,
        GLFW.GLFW_KEY_ENTER to java.awt.event.KeyEvent.VK_ENTER,
        GLFW.GLFW_KEY_TAB to java.awt.event.KeyEvent.VK_TAB,
        GLFW.GLFW_KEY_BACKSPACE to java.awt.event.KeyEvent.VK_BACK_SPACE,
        GLFW.GLFW_KEY_INSERT to java.awt.event.KeyEvent.VK_INSERT,
        GLFW.GLFW_KEY_DELETE to java.awt.event.KeyEvent.VK_DELETE,
        GLFW.GLFW_KEY_RIGHT to java.awt.event.KeyEvent.VK_RIGHT,
        GLFW.GLFW_KEY_LEFT to java.awt.event.KeyEvent.VK_LEFT,
        GLFW.GLFW_KEY_DOWN to java.awt.event.KeyEvent.VK_DOWN,
        GLFW.GLFW_KEY_UP to java.awt.event.KeyEvent.VK_UP,
        GLFW.GLFW_KEY_PAGE_UP to java.awt.event.KeyEvent.VK_PAGE_UP,
        GLFW.GLFW_KEY_PAGE_DOWN to java.awt.event.KeyEvent.VK_PAGE_DOWN,
        GLFW.GLFW_KEY_HOME to java.awt.event.KeyEvent.VK_HOME,
        GLFW.GLFW_KEY_END to java.awt.event.KeyEvent.VK_END,
        GLFW.GLFW_KEY_CAPS_LOCK to java.awt.event.KeyEvent.VK_CAPS_LOCK,
        GLFW.GLFW_KEY_SCROLL_LOCK to java.awt.event.KeyEvent.VK_SCROLL_LOCK,
        GLFW.GLFW_KEY_NUM_LOCK to java.awt.event.KeyEvent.VK_NUM_LOCK,
        GLFW.GLFW_KEY_PRINT_SCREEN to java.awt.event.KeyEvent.VK_PRINTSCREEN,
        GLFW.GLFW_KEY_PAUSE to java.awt.event.KeyEvent.VK_PAUSE,

        // Function keys F1–F24 (F25 has no VK)
        GLFW.GLFW_KEY_F1 to java.awt.event.KeyEvent.VK_F1,
        GLFW.GLFW_KEY_F2 to java.awt.event.KeyEvent.VK_F2,
        GLFW.GLFW_KEY_F3 to java.awt.event.KeyEvent.VK_F3,
        GLFW.GLFW_KEY_F4 to java.awt.event.KeyEvent.VK_F4,
        GLFW.GLFW_KEY_F5 to java.awt.event.KeyEvent.VK_F5,
        GLFW.GLFW_KEY_F6 to java.awt.event.KeyEvent.VK_F6,
        GLFW.GLFW_KEY_F7 to java.awt.event.KeyEvent.VK_F7,
        GLFW.GLFW_KEY_F8 to java.awt.event.KeyEvent.VK_F8,
        GLFW.GLFW_KEY_F9 to java.awt.event.KeyEvent.VK_F9,
        GLFW.GLFW_KEY_F10 to java.awt.event.KeyEvent.VK_F10,
        GLFW.GLFW_KEY_F11 to java.awt.event.KeyEvent.VK_F11,
        GLFW.GLFW_KEY_F12 to java.awt.event.KeyEvent.VK_F12,
        GLFW.GLFW_KEY_F13 to java.awt.event.KeyEvent.VK_F13,
        GLFW.GLFW_KEY_F14 to java.awt.event.KeyEvent.VK_F14,
        GLFW.GLFW_KEY_F15 to java.awt.event.KeyEvent.VK_F15,
        GLFW.GLFW_KEY_F16 to java.awt.event.KeyEvent.VK_F16,
        GLFW.GLFW_KEY_F17 to java.awt.event.KeyEvent.VK_F17,
        GLFW.GLFW_KEY_F18 to java.awt.event.KeyEvent.VK_F18,
        GLFW.GLFW_KEY_F19 to java.awt.event.KeyEvent.VK_F19,
        GLFW.GLFW_KEY_F20 to java.awt.event.KeyEvent.VK_F20,
        GLFW.GLFW_KEY_F21 to java.awt.event.KeyEvent.VK_F21,
        GLFW.GLFW_KEY_F22 to java.awt.event.KeyEvent.VK_F22,
        GLFW.GLFW_KEY_F23 to java.awt.event.KeyEvent.VK_F23,
        GLFW.GLFW_KEY_F24 to java.awt.event.KeyEvent.VK_F24,
        GLFW.GLFW_KEY_F25 to java.awt.event.KeyEvent.VK_UNDEFINED,

        // Keypad
        GLFW.GLFW_KEY_KP_0 to java.awt.event.KeyEvent.VK_NUMPAD0,
        GLFW.GLFW_KEY_KP_1 to java.awt.event.KeyEvent.VK_NUMPAD1,
        GLFW.GLFW_KEY_KP_2 to java.awt.event.KeyEvent.VK_NUMPAD2,
        GLFW.GLFW_KEY_KP_3 to java.awt.event.KeyEvent.VK_NUMPAD3,
        GLFW.GLFW_KEY_KP_4 to java.awt.event.KeyEvent.VK_NUMPAD4,
        GLFW.GLFW_KEY_KP_5 to java.awt.event.KeyEvent.VK_NUMPAD5,
        GLFW.GLFW_KEY_KP_6 to java.awt.event.KeyEvent.VK_NUMPAD6,
        GLFW.GLFW_KEY_KP_7 to java.awt.event.KeyEvent.VK_NUMPAD7,
        GLFW.GLFW_KEY_KP_8 to java.awt.event.KeyEvent.VK_NUMPAD8,
        GLFW.GLFW_KEY_KP_9 to java.awt.event.KeyEvent.VK_NUMPAD9,
        GLFW.GLFW_KEY_KP_DECIMAL to java.awt.event.KeyEvent.VK_DECIMAL,
        GLFW.GLFW_KEY_KP_DIVIDE to java.awt.event.KeyEvent.VK_DIVIDE,
        GLFW.GLFW_KEY_KP_MULTIPLY to java.awt.event.KeyEvent.VK_MULTIPLY,
        GLFW.GLFW_KEY_KP_SUBTRACT to java.awt.event.KeyEvent.VK_SUBTRACT,
        GLFW.GLFW_KEY_KP_ADD to java.awt.event.KeyEvent.VK_ADD,
        GLFW.GLFW_KEY_KP_ENTER to java.awt.event.KeyEvent.VK_ENTER,
        GLFW.GLFW_KEY_KP_EQUAL to java.awt.event.KeyEvent.VK_EQUALS,

        // Modifier keys
        GLFW.GLFW_KEY_LEFT_SHIFT to java.awt.event.KeyEvent.VK_SHIFT,
        GLFW.GLFW_KEY_RIGHT_SHIFT to java.awt.event.KeyEvent.VK_SHIFT,
        GLFW.GLFW_KEY_LEFT_CONTROL to java.awt.event.KeyEvent.VK_CONTROL,
        GLFW.GLFW_KEY_RIGHT_CONTROL to java.awt.event.KeyEvent.VK_CONTROL,
        GLFW.GLFW_KEY_LEFT_ALT to java.awt.event.KeyEvent.VK_ALT,
        GLFW.GLFW_KEY_RIGHT_ALT to java.awt.event.KeyEvent.VK_ALT,
        GLFW.GLFW_KEY_LEFT_SUPER to java.awt.event.KeyEvent.VK_META,
        GLFW.GLFW_KEY_RIGHT_SUPER to java.awt.event.KeyEvent.VK_META,
        GLFW.GLFW_KEY_MENU to java.awt.event.KeyEvent.VK_CONTEXT_MENU
    )
}