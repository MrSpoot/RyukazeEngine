package ryukazev2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.core.Loop;
import ryukazev2.core.Window;
import ryukazev2.manager.RenderManager;

public class Engine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);
    private Window window;
    private RenderManager renderManager;
    private Loop loop;

    public Engine(){
        this.window = new Window();
        //this.renderManager = new RenderManager();
        this.loop = new Loop(this);
    }

    public void render(){

        if(this.window.shouldClose()){
            stop();
        }

        this.window.preRender();



        this.window.postRender();
    }

    public void update(){

    }

    public Engine setWindow(Window window){
        this.window = window;
        this.window.setEngine(this);
        return this;
    }

    public Engine setLoop(Loop loop){
        this.loop = loop;
        return this;
    }

    public Engine build(){
        LOGGER.info("\033[1;32m[BUILD]\u001B[0m Engine instance");
        return this;
    }

    public void run(){
        LOGGER.info("\033[1;32m[RUN]\u001B[0m Engine instance");
        this.loop.run();
    }

    public void stop(){
        LOGGER.info("\033[1;32m[STOP]\u001B[0m Engine instance");
        this.loop.stop();
    }

}
