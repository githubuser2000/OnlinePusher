package onlinePusher;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

public class EnumerateWindows {
    private static final int MAX_TITLE_LENGTH = 1024;

    public  class bufferAndWindowname {
        public String WindowName="";
        public char[] buffer = new char[MAX_TITLE_LENGTH * 2];
    }
    
    public bufferAndWindowname WindowName() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        User32DLL.GetWindowTextW(User32DLL.GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        bufferAndWindowname baw=new bufferAndWindowname();
        baw.WindowName=Native.toString(buffer);
        baw.buffer=buffer;
        return baw;
    }
    public String WindowProcess(char[] buffer) {
        PointerByReference pointer = new PointerByReference();
        User32DLL.GetWindowThreadProcessId(User32DLL.GetForegroundWindow(), pointer);
        Pointer process = Kernel32.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, pointer.getValue());
        Psapi.GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);

    }    

    static class Psapi {
        static { Native.register("psapi"); }
        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    static class Kernel32 {
        static { Native.register("kernel32"); }
        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;
        public static native int GetLastError();
        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    static class User32DLL {
        static { Native.register("user32"); }
        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
        public static native HWND GetForegroundWindow();
        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
    }
}