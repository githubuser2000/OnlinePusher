package onlinePusher;

import java.util.concurrent.Semaphore;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public  class hooks_KeyMouse implements NativeKeyListener, NativeMouseInputListener {
    
	public Semaphore keysema = new Semaphore(2);
	public Semaphore mousema = new Semaphore(2);

	private boolean mouseActive=false;
	private boolean KeyActive=false;
	
	public void setKeysInactive() {
		try {
			keysema.acquire();
        } catch (InterruptedException e) {
        }
		KeyActive=false;
		keysema.release();
	}
	public void setMouseInactive() {
		try {
			mousema.acquire();
        } catch (InterruptedException e) {
        }
		mouseActive=false;
		mousema.release();
	}
	public boolean getKeysActive() {
		return KeyActive;
	}
	public boolean getMouseActive() {
		return mouseActive;
	}
	public void setAllInactive() {
		setKeysInactive();
		setMouseInactive();
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		try {
        	keysema.acquire();
        } catch (InterruptedException e) {
        }
		KeyActive=true;
		keysema.release();
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
	}

	public void addListeners() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.exit(1);
		}
		GlobalScreen.getInstance().addNativeKeyListener(this);
		GlobalScreen.getInstance().addNativeMouseListener(this);
		GlobalScreen.getInstance().addNativeMouseMotionListener(this);
	}

	public void removeListeners() {
		GlobalScreen.getInstance().removeNativeKeyListener(this);
		GlobalScreen.getInstance().removeNativeMouseListener(this);
		GlobalScreen.getInstance().removeNativeMouseMotionListener(this);
		GlobalScreen.unregisterNativeHook();
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		//System.out.println("mouse released1");
		try {
			mousema.acquire();
        } catch (InterruptedException e) {
        }
		mouseActive=true;
		mousema.release();
		//System.out.println("mouse released2");
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		try {
			mousema.acquire();
        } catch (InterruptedException e) {
        }
		mouseActive=true;
		mousema.release();
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		try {
			mousema.acquire();
        } catch (InterruptedException e) {
        }
		mouseActive=true;
		mousema.release();
	}

}
