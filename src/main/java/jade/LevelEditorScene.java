package jade;

import org.lwjgl.BufferUtils;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene{

    private String vertexShaderSrc = "#version 330 core\n" +
            "\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";

    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main(){\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            // position             // color
            0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, // Bottom right 0
            -0.5f,  0.5f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f, // Top left     1
            0.5f,  0.5f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f, // Top right    2
            -0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f  // Bottom left  3
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {
            /*
                x       x

                x       x
            */
            2, 1, 0,  // Top right triangle
            0, 1, 3   // Bottom left triangle
    };

    //vertexArrayObjact vertexBufferObject elementeBufferObject
    private int vaoID, vboID, eboID;

    public LevelEditorScene(){

    }

    @Override
    public void init(){

        //primeiro carregamento e compilacao do vertex shader

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        //PASSAR O SHADER PARA A GPU

        glShaderSource(fragmentID, fragmentShaderSrc);

        glCompileShader(fragmentID);

        // verifica se a erro no processo
        int success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if( success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Erro... 'defaultShader'\n\t Fragment shader compilation failed.");
            System.out.println(glGetShaderi(fragmentID, len));
            assert false:"";
        }

        //liga os shaders e verifica erros

        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // verifica erros
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("Erro 'defaultShader'\n\t Link shader failed");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }

        // geracao VAO, VBO e EBO buffer objects e mandar para GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Criacao  floa buffer do verice
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // criacao do VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        //Criacao de indices e upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //Add o ponteiro do atributo do vertex
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float dt) {
        //bind shader program
        glUseProgram(shaderProgram);
        //Bind the VAO
        glBindVertexArray(vaoID);

        //Habilitar atributo ponteiro do vertex
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        glUseProgram(0);


    }

}
