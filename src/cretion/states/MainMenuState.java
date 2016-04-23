package cretion.states;

import cretion.core.component.common.PositionComponent;
import cretion.core.entity.Entity;
import cretion.core.entity.map.LightEntity;
import cretion.core.entity.player.PlayerEntity;
import cretion.core.entity.player.components.PlayerDirectionComponent;
import cretion.core.entity.player.components.PlayerStateComponent;
import cretion.data.DataManager;
import cretion.data.ProfileData;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import twl.slick.RootPane;

import java.util.ArrayList;
import java.util.List;

public class MainMenuState extends AbstractState {
    private StateBasedGame game;
    private List<Button> buttons;
    private Image background;
    private Image playBubble;
    private Image editorMenuImage;
    private Button editorButton;
    private boolean setup;
    private List<Entity> entities;
    private List<LightEntity> lights;

    public MainMenuState(int _ID) {
        super(_ID);
        game = null;
        buttons = new ArrayList<>();
        setup = false;
        entities = new ArrayList<>();
        lights = new ArrayList<>();
    }

    @Override
    public void init(GameContainer _container, StateBasedGame _game) throws SlickException {
        game = _game;
        background = new Image("/resources/ui/mainmenu.png");
        playBubble = new Image("/resources/ui/play_bubble.png");
        editorMenuImage = new Image("/resources/ui/editor_menu.png");

        for (final ProfileData profile : DataManager.getProfiles()) {
            PlayerEntity player = new PlayerEntity(profile, _container, _game);
            player.getComponent(PlayerStateComponent.class).disable();
            player.getComponent(PlayerDirectionComponent.class).disable();
            entities.add(player);
        }
    }

    @Override
    public void render(GameContainer _container, StateBasedGame _game, Graphics _g) throws SlickException {
        if (!setup)
            return;

        if (lights.size() == 0) {
            {
                LightEntity light = new LightEntity(-0.1f, 0.05f);
                light.setDirection(0, 1);
                light.setFalloff(0.1f, 0.1f, 3.f);
                light.setLightColor(1.0f, 1.0f, 1.0f, 0.5f);
                lights.add(light);
            }
            {
                LightEntity light = new LightEntity(1.1f, 0.05f);
                light.setDirection(0, 1);
                light.setFalloff(0.1f, 0.1f, 3.f);
                light.setLightColor(1.0f, 1.0f, 1.0f, 0.5f);
                lights.add(light);
            }
            {
                LightEntity light = new LightEntity(0.5f, 1.1f);
                light.setDirection(0, -1);
                light.setFalloff(0.1f, 0.1f, 3.f);
                light.setLightColor(1.0f, 1.0f, 1.0f, 0.5f);
                lights.add(light);
            }
        }

        LightEntity.StartShader();
        for (LightEntity light : lights) {
            light.updateShader();
        }

        background.draw();

        for (Entity entity : entities) {
            entity.draw();
        }
        LightEntity.EndShaders();
    }

    @Override
    public void update(GameContainer _container, StateBasedGame _game, int _delta) throws SlickException {
        int i = 0;
        for (Entity entity : entities) {
            buttons.get(i).setPosition((int) entity.getComponent(PositionComponent.class).getX(),
                    (int) entity.getComponent(PositionComponent.class).getY() - 50);
            entity.update(_delta);
            i++;
        }
    }

    @Override
    protected RootPane createRootPane() {
        RootPane rootPane = super.createRootPane();

        for (final ProfileData character : DataManager.getProfiles()) {
            Button button = new Button(character.getNameData()) {
                private boolean switcher = false;
                private float floaterAmount = 0;
                private float accelerationAmount = 0;

                @Override
                public void paint(GUI _gui) {
                    if (switcher) {
                        accelerationAmount -= 0.002f;
                        floaterAmount -= accelerationAmount;
                    } else {
                        accelerationAmount += 0.002f;
                        floaterAmount += accelerationAmount;
                    }
                    playBubble.draw(getX() + 30 - playBubble.getWidth() / 2, getY() + floaterAmount);
                    if (floaterAmount > 10 || floaterAmount < 0) {
                        switcher = !switcher;
                    }
                }
            };
            button.addCallback(() -> {
                DataManager.setCurrentProfile(character.getNameData());
                game.enterState(1);
            });
            buttons.add(button);
            rootPane.add(button);
        }

        editorButton = new Button() {
            @Override
            public void paint(GUI _gui) {
                editorMenuImage.draw(getX(), getY());
            }
        };
        editorButton.addCallback(() ->
            game.enterState(2)
        );
        rootPane.add(editorButton);

        return rootPane;
    }

    @Override
    protected void layoutRootPane() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setSize(80, 80);
            buttons.get(i).setPosition(300 + i * 200, 476);
            try {
                entities.get(i).getComponent(PositionComponent.class).setX(buttons.get(i).getX());
                entities.get(i).getComponent(PositionComponent.class).setY(buttons.get(i).getY() + 40);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        editorButton.setSize(editorMenuImage.getWidth(), editorMenuImage.getHeight());
        editorButton.setPosition(getRootPane().getWidth() / 2 - editorMenuImage.getWidth() / 2, 300);
        setup = true;
    }
}
