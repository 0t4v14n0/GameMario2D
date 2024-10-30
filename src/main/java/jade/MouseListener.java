package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseApertoBotao[] = new boolean[3];
    private boolean estaArastando;

    private MouseListener(){
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get (){

        if(MouseListener.instance == null){
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    public static void mousePosCallBack(long window, double xpos,double ypos){
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().estaArastando = get().mouseApertoBotao[0] || get().mouseApertoBotao[0] || get().mouseApertoBotao[0];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(action == GLFW_PRESS){
            if(button < get().mouseApertoBotao.length){
                get().mouseApertoBotao[button] = true;
            }
        } else if (action == GLFW_RELEASE){
            if(button < get().mouseApertoBotao.length) {
                get().mouseApertoBotao[button] = false;
                get().estaArastando = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame(){
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getDx(){
        return (float)(get().lastX - get().xPos);
    }

    public static float getDy(){
        return (float)(get().lastY - get().yPos);
    }

    public static float getScrollX(){
        return(float)(get().scrollX);
    }

    public static float getScrollY(){
        return(float)(get().scrollY);
    }

    public static boolean estaArastando(){
        return get().estaArastando;
    }

    public static boolean mouseApertoBotao(int button){
        if(button < get().mouseApertoBotao.length){
            return get().mouseApertoBotao[button];
        } else{
            return false;
        }
    }
}
