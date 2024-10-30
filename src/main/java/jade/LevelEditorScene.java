package jade;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene{

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene(){
        System.out.println("edicao de scena");
    }

    @Override
    public void update(float dt) {

        if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)){
            changingScene = true;
        }

        if(changingScene && timeToChangeScene > 0){
            timeToChangeScene -= dt;
            Window.get().r -= dt * 5.9f;
            Window.get().g -= dt * 5.9f;
            Window.get().b -= dt * 5.9f;

        }else if (changingScene){
            Window.changedScene(1);
        }

    }


}
