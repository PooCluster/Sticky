package game.logic.button;

import game.Main;
import game.SoundEngine;
import game.input.Mouse;
import game.logic.MenuText;
import java.util.ArrayList;
import java.util.Scanner;

public class Button {
    
    protected int target;
    protected int x;
    protected int y;
    protected String commandWord;
    protected int type;
    
    protected ArrayList<MenuText> descriptor = new ArrayList<>();
    protected ArrayList<Double> beginnings = new ArrayList<>();
    protected ArrayList<Double> endings = new ArrayList<>();
    
    protected int action = -1; //-1 is no action
    
    /*
    protected Clip[] sounds = new Clip[2]; //0-pop 1-click
    protected AudioInputStream[] input = new AudioInputStream[2];
    protected FloatControl[] gainControl = new FloatControl[2];
*/
    protected boolean popped;
    
    protected ArrayList<ArrayList<MenuText>> commands = new ArrayList<>();
    
    public Button(String information, int type) {
        /*
        for (int i = 0; i < 2; i++) {
            try {
                sounds[i] = AudioSystem.getClip();
                input[i] = AudioSystem.getAudioInputStream(game.Game.class.getResource("resources/audio/sfx/Button/" + (i + 1) + ".wav"));
                sounds[i].open(input[i]);
                gainControl[i] = (FloatControl)sounds[i].getControl(FloatControl.Type.MASTER_GAIN);
                gainControl[i].setValue(game.Game.sfxVolume);
                sounds[i].stop();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        this.type = type;
        Scanner scan = new Scanner(information);
        target = scan.nextInt();
        x = scan.nextInt() * 16; //Grid everything.
        y = scan.nextInt() * 16; //Grid all text.
        commandWord = scan.next();
        createText(commandWord);
        
    }
    
    protected void createText(String commandWord) {
        descriptor.clear();
        commands.clear();
        beginnings.clear();
        endings.clear();
        outlines.clear();
        boolean typeOfWord = false;
        commands.add(new ArrayList<MenuText>());
        //false descriptor
        //true command
        for (int i = 0; i < commandWord.length(); i++) {
            //Creating the phrase.
            switch (commandWord.charAt(i)) {
                case ']':
                    commands.add(new ArrayList<MenuText>());
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 56)); //' '    
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 56));
                    }
                    break;
                case '[':
                    typeOfWord = true;
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 56)); //' '    
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 56));
                    }
                    break;
                case ' ':
                case '_':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 56)); //' '    
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 56));
                    }
                    break;
                case '+':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 48));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 48));
                    }
                    break;
                case '-':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 49));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 49));
                    }
                    break;
                case '.':
                    if (!typeOfWord) {
                       descriptor.add(new MenuText(x + (i * 16), y, 38)); 
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 38));
                    }
                    break;
                case ',':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 37));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 37));
                    }
                    break;
                case 39:
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 39));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 39));
                    }
                    break;
                case '=':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 52));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 52));
                    }
                    break;
                case '/':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 51));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 51));
                    }
                    break;
                case '?':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 40));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 40));
                    }
                    break;
                case '0':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 26));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 26));
                    }
                    break;
                case '1':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 27));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 27));
                    }
                    break;
                case '2':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 28));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 28));
                    }
                    break;
                case '3':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 29));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 29));
                    }
                    break;
                case '4':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 30));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 30));
                    }
                    break;
                case '5':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 31));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 31));
                    }
                    break;
                case '6':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 32));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 32));
                    }
                    break;
                case '7':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 33));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 33));
                    }
                    break;
                case '8':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 34));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 34));
                    }
                    break;
                case '9':
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, 35));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, 35));
                    }
                    break;
                default:
                    if (!typeOfWord) {
                        descriptor.add(new MenuText(x + (i * 16), y, commandWord.charAt(i) - 'A'));
                    } else {
                        commands.get(commands.size() - 1).add(new MenuText(x + (i * 16), y, commandWord.charAt(i) - 'A'));
                    }
                    break;
            }
            //Defining the outlines.
            if (commandWord.charAt(i) == '[') beginnings.add((double)(x + (i * 16)));
            if (commandWord.charAt(i) == ']') endings.add((double)(x + (i * 16)));
            
            
        }
        for (int i = 0; i < beginnings.size(); i++) {
            //ArrayList of 2D ArrayList. We don't know how many outline boxes there could be.
            //2D list would store outline and outline with offset.
            ArrayList<MenuText>[] outline = new ArrayList[2];
            outline[0] = new ArrayList<>();
            outline[1] = new ArrayList<>();
            //Without offset.
            int beginning = (int)beginnings.get(i).doubleValue();
            int ending = (int)endings.get(i).doubleValue();
            outline[0].add(new MenuText(beginning + 8, y - 8, 42));
            outline[0].add(new MenuText(beginning + 8, y + 8, 45));
            for (int j = beginning + 16; j < ending; j += 16) {
                outline[0].add(new MenuText(j, y - 8, 43));
                outline[0].add(new MenuText(j, y + 8, 46));
            }
            outline[0].add(new MenuText(ending - 8, y - 8, 44));
            outline[0].add(new MenuText(ending - 8, y + 8, 47));
            //With offset.
            outline[1].add(new MenuText(beginning + 8 - 1, y - 8 + 1, 42));
            outline[1].add(new MenuText(beginning + 8 - 1, y + 8 + 1, 45));
            for (int j = beginning + 16; j < ending; j += 16) {
                outline[1].add(new MenuText(j - 1, y - 8 + 1, 43));
                outline[1].add(new MenuText(j - 1, y + 8 + 1, 46));
            }
            outline[1].add(new MenuText(ending - 8 - 1, y - 8 + 1, 44));
            outline[1].add(new MenuText(ending - 8 - 1, y + 8 + 1, 47));
            //Last step.
            outlines.add(outline);
        }
        if (beginnings.isEmpty()) {
            ArrayList<MenuText>[] fill = new ArrayList[2];
            fill[0] = new ArrayList<MenuText>();
            fill[1] = new ArrayList<MenuText>();
            outlines.add(fill);
        }
    }
    
    protected ArrayList<ArrayList<MenuText>[]> outlines = new ArrayList<>();
    
    protected int createOutline;
    protected int saveIndex = -1; //Acts like a boolean, but defines which box was clicked.
    
    protected boolean mouseInitial;
    protected int offset;
    
    public void update() {
        Double mouseX = Mouse.x / Main.upScale; //Converts from coordinates to pixels on the screen.
        Double mouseY = Mouse.y / Main.upScaleY;
        createOutline = -1;
        if (mouseY >= y && mouseY <= y + 16) {
            for (int i = 0; i < beginnings.size(); i++) {
                if (mouseX >= beginnings.get(i) + 16 && mouseX <= endings.get(i)) { //Added 16, because the beginning is usually just a blank space.
                    createOutline = i;
                    if (!popped) {
                        SoundEngine.startClick1();
                        /*
                        if (SoundEngine.isSFXPlaying()) {
                            sounds[0].stop();
                            sounds[0].setFramePosition(0);
                            sounds[0].start();
                        }                  
                        */
                        popped = true;
                    }
                    if (!mouseInitial && Mouse.leftClick && saveIndex == -1) {
                        saveIndex = createOutline;
                        offset = 1;
                        SoundEngine.startClick2();
                        /*
                        if (SoundEngine.isSFXPlaying()) {
                            sounds[1].stop();
                            sounds[1].setFramePosition(0);
                            sounds[1].start();
                        }
                        */
                    }
                    break;
                }
            }
        }
        if (createOutline == -1) popped = false;
        if (Mouse.leftClick) mouseInitial = true;
        if (!Mouse.leftClick) {
            if (saveIndex != -1 && saveIndex == createOutline) {
                action = saveIndex;
                SoundEngine.startClick2();
                /*
                if (SoundEngine.isSFXPlaying()) {
                    sounds[1].stop();
                    sounds[1].setFramePosition(0);
                    sounds[1].start();
                }
                */
            }
            saveIndex = -1;
            mouseInitial = false;
            offset = 0;
        }
        //Action will be dealt with by the sub classes.
    }
    
    
    
    public void render() {
        //MAKE THIS MORE EFFICIENT LATER.
        for (int i = 0; i < descriptor.size(); i++) descriptor.get(i).render();
        for (int i = 0; i < commands.size(); i++) {
            for (int j = 0; j < commands.get(i).size(); j++) {
                commands.get(i).get(j).render();
            }
        }
        if (createOutline != -1 && !mouseInitial) {
            for (int i = 0; i < outlines.get(createOutline)[0].size(); i++) outlines.get(createOutline)[0].get(i).render();
        } else if (saveIndex != -1 && mouseInitial) { //When mouse clicked.
            for (int i = 0; i < outlines.get(saveIndex)[1].size(); i++) outlines.get(saveIndex)[1].get(i).render();
        }
    }
    
}