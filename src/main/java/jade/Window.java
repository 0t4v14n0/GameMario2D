package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int largura;
    private int altura;
    private String titulo;
    private long glfwWindow;

    public float r, g, b, a;

    private boolean fadeToBlack = false;

    private static Window window = null;

    private static int currentSceneIndex = -1;

    private static Scene currentScene = null;

    private Window() {
        this.largura = 1920;
        this.altura = 1080;
        this.titulo = "Mario";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static void changedScene(int newScene){

        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;
            default:
                assert false : "scena nao encontrada '" + newScene + "'";
                break;
        }
    }

    public static Window get() {

        if(Window.window == null){
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        //System.out.println("foi");

        init();
        loop();

        //liberar memoria
        //glfwFreeCallBacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //fim do GLFW e retorna erro
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        // setup retorno de erro
        GLFWErrorCallback.createPrint(System.err).set();

        //inicialiacao GLFW
        if(!glfwInit()) {
            throw new IllegalArgumentException("incapaz de inicializar GLFW");
        }

        //configuracao GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Criacao de tela
        glfwWindow = glfwCreateWindow(this.largura,this.altura,this.titulo,NULL,NULL);
        if(glfwWindow == NULL){
            throw new IllegalStateException("falha ao criar tela GLFW Window");
        }

        //mouse inicio
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallBack);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        //teclado inicio
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //OpenGL
        glfwMakeContextCurrent(glfwWindow);

        //variavel v-sync
        glfwSwapInterval(1);

        //visibilidade Window
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        Window.changedScene(0);

    }

    public void loop(){

        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)){

            //eventos
            glfwPollEvents();

            //cor de fundo
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

           if (dt >= 0) {
               currentScene.update(dt);
           }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;

        }
    }
}
