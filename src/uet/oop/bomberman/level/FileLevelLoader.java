package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.*;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
     * từ ma trận bản đồ trong tệp cấu hình
     */
    private static char[][] _map;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    public FileLevelLoader(Board board, int level, boolean isMulti) throws LoadLevelException {
        super(board, level, isMulti);
        System.out.println("hi");
    }

    @Override
    public void loadLevel(int level) {
        try {
//			String levelInput = "D:\\Project\\Java\\bomber\\bomberman-starter-starter-project-1\\bomberman-starter-starter-project-1\\res\\levels\\Level1.txt";
            BufferedReader in = new BufferedReader(new FileReader(getClass().getResource("/levels/Level" + String.valueOf(level) + ".txt").getFile()));
            //Multiplayer BufferedReader in = new BufferedReader(new FileReader(getClass().getResource("/levels/multiplayerLevel/Level" + String.valueOf(level) + ".txt").getFile()));
            //BufferedReader in = new BufferedReader(new FileReader("levels/Level" + String.valueOf(level) + ".txt"));
            // Read level + height + width
            String[] x = in.readLine().split(" ");
            this._level = Integer.valueOf(x[0]);
            this._width = Integer.valueOf(x[2]);
            this._height = Integer.valueOf(x[1]);
            this._map = new char[this._height][this._width];
//			System.out.println(this._width);
//			System.out.println(this._height);
            for (int i = 0; i < this._height; i++) {
                String curRow = in.readLine();
                for (int j = 0; j < this._width; j++) {
//					System.out.println(curRow);
                    this._map[i][j] = curRow.charAt(j);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMultiMap(int level){
        try {
            BufferedReader in = new BufferedReader(new FileReader(getClass().getResource("/levels/multiplayerLevel/Level" + String.valueOf(level) + ".txt").getFile()));
            String[] x = in.readLine().split(" ");
            this._level = Integer.valueOf(x[0]);
            this._width = Integer.valueOf(x[2]);
            this._height = Integer.valueOf(x[1]);
            this._map = new char[this._height][this._width];
            for (int i = 0; i < this._height; i++) {
                String curRow = in.readLine();
                for (int j = 0; j < this._width; j++) {
                    this._map[i][j] = curRow.charAt(j);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createEntities() {
        int numberOfPlayer = 0;
        for (int i = 0; i < this._height; i++) {
            for (int j = 0; j < this._width; j++) {
                if (this._map[i][j] == 'p') {
                    // Add Bomber
                    int xBomber = j, yBomber = i;
                    numberOfPlayer++;
                    Bomber bomber = new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board,numberOfPlayer);
                    if (numberOfPlayer == 1 && _level>1){
                        Bomber preBomber = _board.getBomber();
                        bomber.passParameter(preBomber);
                    }
                    _board.addCharacter(bomber);
                    Screen.setOffset(0, 0);
                    _board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
                } else if (this._map[i][j] == '*') {
                    // Add brick
                    int xB = j, yB = i;
                    _board.addEntity(xB + yB * _width,
                            new LayeredEntity(xB, yB,
                                    new Grass(xB, yB, Sprite.grass),
                                    new Brick(xB, yB, Sprite.brick)
                            )
                    );
                } else if (this._map[i][j] == '1') {
                    // Add Balloon
                    int xE = j, yE = i;
                    _board.addCharacter(new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
                    _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
                }  else if (this._map[i][j] == '2') {
                    // Add Oneal
                    int xE = j, yE = i;
                    _board.addCharacter(new Oneal(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
                    _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
                } else if (this._map[i][j] == '3') {
                    // Add FlameMonster
                    int xE = j, yE = i;
                    _board.addCharacter(new FlameMonster(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
                    _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
                }else if (this._map[i][j] == '4') {
                    // Add Balloon
                    int xE = j, yE = i;
                    _board.addCharacter(new Ghost(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
                    _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
                }else if (this._map[i][j] == '0') {
                    // Add Boss
                    int xE = j, yE = i;
                    Boss boss = new Boss(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board);
                    _board.addCharacter(boss);
                    _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
                    _board.setBoss(boss);
                }else if (this._map[i][j] == 'b') {
                    // Add BombItem
                    int xI = j, yI = i;
                    _board.addEntity(xI + yI * _width,
                            new LayeredEntity(xI, yI,
                                    new Grass(xI, yI, Sprite.grass),
                                    new BombItem(xI, yI, Sprite.powerup_bombs),
                                    new Brick(xI, yI, Sprite.brick)
                            )
                    );
                } else if (this._map[i][j] == 'f') {
                    // Add FlameItem
                    int xI = j, yI = i;
                    _board.addEntity(xI + yI * _width,
                            new LayeredEntity(xI, yI,
                                    new Grass(xI, yI, Sprite.grass),
                                    new FlameItem(xI, yI, Sprite.powerup_flames),
                                    new Brick(xI, yI, Sprite.brick)
                            )
                    );
                } else if (this._map[i][j] == 's') {
                    // Add SpeedItem
                    int xI = j, yI = i;
                    _board.addEntity(xI + yI * _width,
                            new LayeredEntity(xI, yI,
                                    new Grass(xI, yI, Sprite.grass),
                                    new SpeedItem(xI, yI, Sprite.powerup_speed),
                                    new Brick(xI, yI, Sprite.brick)
                            )
                    );
                } else if (this._map[i][j] == 'g') {
                    // Add ShieldItem
                    int xI = j, yI = i;
                    _board.addEntity(xI + yI * _width,
                            new LayeredEntity(xI, yI,
                                    new Grass(xI, yI, Sprite.grass),
                                    new ShieldItem(xI, yI, Sprite.powerup_shield),
                                    new Brick(xI, yI, Sprite.brick)
                            )
                    );
                } else if (this._map[i][j] == 'd') {
                    // Add SuperBombItem
                    int xI = j, yI = i;
                    _board.addEntity(xI + yI * _width,
                            new LayeredEntity(xI, yI,
                                    new Grass(xI, yI, Sprite.grass),
                                    new SuperBombItem(xI, yI, Sprite.powerup_superbomb),
                                    new Brick(xI, yI, Sprite.brick)
                            )
                    );
                }else if (this._map[i][j] == 'w') {
                    // Add WallpassItem
                    int xI = j, yI = i;
                    _board.addEntity(xI + yI * _width,
                            new LayeredEntity(xI, yI,
                                    new Grass(xI, yI, Sprite.grass),
                                    new WallpassItem(xI, yI, Sprite.powerup_wallpass),
                                    new Brick(xI, yI, Sprite.brick)
                            )
                    );
                } else if (this._map[i][j] == '#') {
                    // Add Wall
                    int pos = j + i * _width;
                    if (i == this._height - 1 || j == this._width - 1 || i == 0 || j == 0)
                        _board.addEntity(pos, new Wall(j, i, Sprite.wall, true));
                    else
                        _board.addEntity(pos, new Wall(j, i, Sprite.wall));
                } else if (this._map[i][j] == ' ') {
                    // Add Grass
                    int pos = j + i * _width;
                    _board.addEntity(pos, new Grass(j, i, Sprite.grass));
                } else {
                    // Add portal
                    int xI = j;
                    int yI = i;
                    int pos = xI + yI * _width;
                    _board.setPortal(new Portal(xI, yI, Sprite.portal));
                    _board.addEntity(pos, new LayeredEntity(xI, yI,
                            new Grass(xI, yI, Sprite.grass),
                            new Portal(xI, yI, Sprite.portal),
                            new Brick(xI, yI, Sprite.brick)));
                }
            }

        }
        Game.setNumberOfPlayer(numberOfPlayer);
    }

}