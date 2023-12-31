package ryukaze.core;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.graphics.Shader;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class PostProcessingRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostProcessingRenderer.class);
    private Shader shader;
    private final int fbo;
    @Getter
    private final int texture;
    private final int rbo;

    public PostProcessingRenderer(){

        this.fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER,this.fbo);

        this.texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D,this.texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, Engine.getWindow().getWidth(), Engine.getWindow().getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glBindTexture(GL_TEXTURE_2D, 0);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.texture, 0);

        this.rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, Engine.getWindow().getWidth(), Engine.getWindow().getHeight());
        glBindRenderbuffer(GL_RENDERBUFFER, 0);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            LOGGER.warn("Framebuffer Error : Framebuffer is not completed");
        }

        this.shader = new Shader("src/main/resources/shader/default.post.vert","src/main/resources/shader/default.post.frag");

        glBindFramebuffer(GL_FRAMEBUFFER,0);
    }

    public void bindPostProcessingRender(){
        glBindFramebuffer(GL_FRAMEBUFFER, this.fbo);
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // nous n’utilisons pas de tampon de profondeur
        glEnable(GL_DEPTH_TEST);
    }

    public void unbindPostProcessingRender(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0); // retour au framebuffer par défaut
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

}
