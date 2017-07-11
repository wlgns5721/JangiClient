package com.example.jihoon.clientsocket;


import java.util.Vector;

/**
 * Created by Ji Hoon on 2016-02-17.
 */
public class Path {
    Piece piece;
    Vector<Coordinate> path_vec;
    Vector<Piece> piece_vec;
    public Path(Piece _piece,Vector<Piece> _piece_vec) {
        piece = _piece;
        path_vec = new Vector<Coordinate>();
        piece_vec = _piece_vec;

    }

    void GetPath() {
        path_vec.clear();
        if (piece.character.equals("Cha"))
            GetPath_Cha();
        else if (piece.character.equals("Ma"))
            GetPath_Ma();
        else if (piece.character.equals("Sang"))
            GetPath_Sang();
        else if (piece.character.equals("Po"))
            GetPath_Po();
        else if (piece.character.equals("Sa"))
            GetPath_Sa();
        else if (piece.character.equals("Jol"))
            GetPath_Jol();
        else if (piece.character.equals("King"))
            GetPath_King();

    }
    void GetPath_Cha() {
        for (int i=piece.x+1;i<9;i++) {
            for (int s=0; s< piece_vec.size();s++) {
                if (piece.y== piece_vec.get(s).y && i== piece_vec.get(s).x) {
                    if (piece_vec.get(s).team!=piece.team) {
                        Coordinate coordinate = new Coordinate(i, piece.y);
                        path_vec.add(coordinate);
                    }
                    i+=10;
                    break;
                }
                else if (s+1== piece_vec.size()) {
                    Coordinate coordinate = new Coordinate(i,piece.y);
                    path_vec.add(coordinate);
                }
            }
        }
        for (int i=piece.x-1;i>=0;i--) {
            for (int s=0; s< piece_vec.size();s++) {
                if (piece.y== piece_vec.get(s).y && i== piece_vec.get(s).x) {
                    if (piece_vec.get(s).team!=piece.team) {
                        Coordinate coordinate = new Coordinate(i, piece.y);
                        path_vec.add(coordinate);
                    }
                    i-=10;
                    break;
                }else if (s+1== piece_vec.size()) {
                    Coordinate coordinate = new Coordinate(i,piece.y);
                    path_vec.add(coordinate);
                }

            }
        }
        for (int i=piece.y+1;i<10;i++) {
            for (int s=0; s< piece_vec.size();s++) {
                if (i== piece_vec.get(s).y && piece.x== piece_vec.get(s).x) {
                    if (piece_vec.get(s).team!=piece.team) {
                        Coordinate coordinate = new Coordinate(piece.x,i);
                        path_vec.add(coordinate);
                    }
                    i+=10;
                    break;
                }else if (s+1== piece_vec.size()) {
                    Coordinate coordinate = new Coordinate(piece.x,i);
                    path_vec.add(coordinate);
                }

            }
        }
        for (int i=piece.y-1;i>=0;i--) {
            for (int s=0; s< piece_vec.size();s++) {
                if (i== piece_vec.get(s).y && piece.x== piece_vec.get(s).x ) {
                    if (piece_vec.get(s).team!=piece.team) {
                        Coordinate coordinate = new Coordinate(piece.x,i);
                        path_vec.add(coordinate);
                    }
                    i-=10;
                    break;
                }
                else if (s+1== piece_vec.size()) {
                    Coordinate coordinate = new Coordinate(piece.x,i);
                    path_vec.add(coordinate);
                }

            }
        }

        Vector<Coordinate> moveable_vec = new Vector<Coordinate>();
        if (piece.x==4 &&(piece.y==1 || piece.y==8) ) {
            Coordinate coordinate = new Coordinate(piece.x+1,piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x-1,piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x+1,piece.y-1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x-1,piece.y-1);
            moveable_vec.add(coordinate);
        }
        else if (piece.x==3 || piece.x==5) {
            if (piece.y==0 || piece.y==2) {
                Coordinate coordinate = new Coordinate(4,1);
                moveable_vec.add(coordinate);
                for (int i=0; i<piece_vec.size(); i++) {
                    if (piece_vec.get(i).x==4 && piece_vec.get(i).y==1)
                        break;
                    else if (i+1==piece_vec.size()) {
                        int X = 4-piece.x;
                        int Y = 1-piece.y;
                        coordinate = new Coordinate(4+X,1+Y);
                        moveable_vec.add(coordinate);
                    }

                }
            }
            else if (piece.y==7 || piece.y==9) {
                Coordinate coordinate = new Coordinate(4,1);
                path_vec.add(coordinate);
                for (int i=0; i<piece_vec.size(); i++) {
                    if (piece_vec.get(i).x==4 && piece_vec.get(i).y==8)
                        break;
                    else if (i+1==piece_vec.size()) {
                        int X = 4-piece.x;
                        int Y = 8-piece.y;
                        coordinate = new Coordinate(4+X,8+Y);
                        moveable_vec.add(coordinate);
                    }

                }
            }
        }
        CheckObject(moveable_vec);
    }

    void GetPath_Ma() {
        Vector<Coordinate> moveable_vec = new Vector<Coordinate>();
        Coordinate coordinate;
        for (int i = 0; i < piece_vec.size(); i++) {
            if ((piece.x + 1 == piece_vec.get(i).x && piece.y == piece_vec.get(i).y))
                break;
            else if (i+1==piece_vec.size()) {
                if (piece.x+2<=8 && piece.y+1<=9) {
                    coordinate = new Coordinate(piece.x + 2, piece.y + 1);
                    moveable_vec.add(coordinate);
                }
                if (piece.x+2<=8 && piece.y-1>=0) {
                    coordinate = new Coordinate(piece.x + 2, piece.y - 1);
                    moveable_vec.add(coordinate);
                }
            }
        }
        for (int i = 0; i < piece_vec.size(); i++) {
            if ((piece.x - 1 == piece_vec.get(i).x && piece.y == piece_vec.get(i).y))
                break;
            else if (i+1 == piece_vec.size()) {
                if (piece.x-2>=0 && piece.y+1<=9) {
                    coordinate = new Coordinate(piece.x - 2, piece.y + 1);
                    moveable_vec.add(coordinate);
                }
                if (piece.x-2>=0 && piece.y-1>=0) {
                    coordinate = new Coordinate(piece.x - 2, piece.y - 1);
                    moveable_vec.add(coordinate);
                }
            }
        }
        for (int i = 0; i < piece_vec.size(); i++) {
            if ((piece.x == piece_vec.get(i).x && piece.y + 1 == piece_vec.get(i).y))
                break;
            else if (i+1==piece_vec.size()) {
                if (piece.x+1<=8 && piece.y+2<=9) {
                    coordinate = new Coordinate(piece.x + 1, piece.y + 2);
                    moveable_vec.add(coordinate);
                }
                if (piece.x-1>=0 && piece.y+2<=9) {
                    coordinate = new Coordinate(piece.x - 1, piece.y + 2);
                    moveable_vec.add(coordinate);
                }
            }
        }

        for (int i = 0; i < piece_vec.size(); i++) {
            if ((piece.x == piece_vec.get(i).x && piece.y - 1 == piece_vec.get(i).y))
                break;
            else if (i+1==piece_vec.size()) {
                if (piece.x+1<=8 && piece.y-2>=0) {
                    coordinate = new Coordinate(piece.x + 1, piece.y - 2);
                    moveable_vec.add(coordinate);
                }
                if (piece.x-1>=0 && piece.y-2>=0) {
                    coordinate = new Coordinate(piece.x - 1, piece.y - 2);
                    moveable_vec.add(coordinate);
                }
            }
        }
        CheckObject(moveable_vec);
    }

    void GetPath_Sang() {
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x+1== piece_vec.get(i).x && piece.y== piece_vec.get(i).y) || (piece.x+2== piece_vec.get(i).x && piece.y+1== piece_vec.get(i).y)|| (piece.x+3== piece_vec.get(i).x && piece.y+2== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team && piece.x+3== piece_vec.get(i).x && piece.y+2== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size()&& piece.x+3<=8 && piece.y+2<=9) {
                Coordinate coordinate = new Coordinate(piece.x+3,piece.y+2);
                path_vec.add(coordinate);
            }
        }
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x+1== piece_vec.get(i).x && piece.y== piece_vec.get(i).y) || (piece.x+2== piece_vec.get(i).x && piece.y-1== piece_vec.get(i).y)|| (piece.x+3== piece_vec.get(i).x && piece.y-2== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team&& piece.x+3== piece_vec.get(i).x && piece.y-2== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size() && piece.x+3<=8 && piece.y-2>=0) {
                Coordinate coordinate = new Coordinate(piece.x+3,piece.y-2);
                path_vec.add(coordinate);
            }
        }
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x== piece_vec.get(i).x && piece.y+1== piece_vec.get(i).y) || (piece.x+1== piece_vec.get(i).x && piece.y+2== piece_vec.get(i).y)|| (piece.x+2== piece_vec.get(i).x && piece.y+3== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team&& piece.x+2== piece_vec.get(i).x && piece.y+3== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size() && piece.x+2<=8 && piece.y+3<=9) {
                Coordinate coordinate = new Coordinate(piece.x+2,piece.y+3);
                path_vec.add(coordinate);
            }
        }
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x== piece_vec.get(i).x && piece.y+1== piece_vec.get(i).y) || (piece.x-1== piece_vec.get(i).x && piece.y+2== piece_vec.get(i).y)|| (piece.x-2== piece_vec.get(i).x && piece.y+3== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team&& piece.x-2== piece_vec.get(i).x && piece.y+3== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size() && piece.x-2>=0 && piece.y+3<=9) {
                Coordinate coordinate = new Coordinate(piece.x-2,piece.y+3);
                path_vec.add(coordinate);
            }
        }
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x-1== piece_vec.get(i).x && piece.y== piece_vec.get(i).y) || (piece.x-2== piece_vec.get(i).x && piece.y+1== piece_vec.get(i).y)|| (piece.x-3== piece_vec.get(i).x && piece.y+2== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team&& piece.x-3== piece_vec.get(i).x && piece.y+2== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size() && piece.x-3>=0 && piece.y+2<=9) {
                Coordinate coordinate = new Coordinate(piece.x-3,piece.y+2);
                path_vec.add(coordinate);
            }
        }
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x-1== piece_vec.get(i).x && piece.y== piece_vec.get(i).y) || (piece.x-2== piece_vec.get(i).x && piece.y-1== piece_vec.get(i).y)|| (piece.x-3== piece_vec.get(i).x && piece.y-2== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team&& piece.x-3== piece_vec.get(i).x && piece.y-2== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size() && piece.x-3>=0 && piece.y-2>=0) {
                Coordinate coordinate = new Coordinate(piece.x-3,piece.y-2);
                path_vec.add(coordinate);
            }
        }
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x== piece_vec.get(i).x && piece.y-1== piece_vec.get(i).y) || (piece.x+1== piece_vec.get(i).x && piece.y-2== piece_vec.get(i).y)|| (piece.x+2== piece_vec.get(i).x && piece.y-3== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team&& piece.x+2== piece_vec.get(i).x && piece.y-3== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size() && piece.x+2<=8 && piece.y-3>=0) {
                Coordinate coordinate = new Coordinate(piece.x+2,piece.y-3);
                path_vec.add(coordinate);
            }
        }
        for (int i=0; i< piece_vec.size(); i++) {
            if ((piece.x== piece_vec.get(i).x && piece.y-1== piece_vec.get(i).y) || (piece.x-2== piece_vec.get(i).x && piece.y-3== piece_vec.get(i).y)) {
                if (!(piece.team!= piece_vec.get(i).team&& piece.x-2== piece_vec.get(i).x && piece.y-3== piece_vec.get(i).y))
                    break;
            }
            if (i+1== piece_vec.size() && piece.x-2>=0 && piece.y-3>=0) {
                Coordinate coordinate = new Coordinate(piece.x-2,piece.y-3);
                path_vec.add(coordinate);
            }
        }
    }

    void GetPath_Po() {
        boolean obs_flag=false;
        for (int i=piece.x+1;i<9;i++) {
            for (int s=0; s< piece_vec.size();s++) {
                if (piece.y== piece_vec.get(s).y && i== piece_vec.get(s).x) {
                    if (obs_flag && !(piece_vec.get(s).character.equals("Po")) && piece_vec.get(s).team != piece.team) {
                        Coordinate coordinate = new Coordinate(i, piece.y);
                        path_vec.add(coordinate);
                        i += 10;
                        break;
                    }
                    else if (piece_vec.get(s).character.equals("Po") || obs_flag) {
                        i += 10;
                        break;
                    }
                    else if (!(piece_vec.get(s).character.equals("Po"))) {
                        obs_flag = true;
                        break;
                    }
                }
                else if (s+1== piece_vec.size() && obs_flag) {
                    Coordinate coordinate = new Coordinate(i,piece.y);
                    path_vec.add(coordinate);
                }
            }
        }
        obs_flag=false;
        for (int i=piece.x-1;i>=0;i--) {
            for (int s=0; s< piece_vec.size();s++) {
                if (piece.y== piece_vec.get(s).y && i== piece_vec.get(s).x) {
                    if (obs_flag && !(piece_vec.get(s).character.equals("Po")) && piece_vec.get(s).team!=piece.team) {
                        Coordinate coordinate = new Coordinate(i, piece.y);
                        path_vec.add(coordinate);
                        i-=10;
                        break;
                    }
                    else if (piece_vec.get(s).character.equals("Po") || obs_flag) {
                        i-=10;
                        break;
                    }
                    else if (!(piece_vec.get(s).character.equals("Po"))) {
                        obs_flag = true;
                        break;
                    }
                }
                else if (s+1== piece_vec.size() && obs_flag) {
                    Coordinate coordinate = new Coordinate(i,piece.y);
                    path_vec.add(coordinate);
                }

            }
        }
        obs_flag=false;
        for (int i=piece.y+1;i<10;i++) {
            for (int s=0; s< piece_vec.size();s++) {
                if (i== piece_vec.get(s).y && piece.x== piece_vec.get(s).x) {
                    if (obs_flag && !(piece_vec.get(s).character.equals("Po")) && piece_vec.get(s).team!=piece.team) {
                        Coordinate coordinate = new Coordinate(piece.x,i);
                        path_vec.add(coordinate);
                        i+=10;
                        break;
                    }
                    else if (piece_vec.get(s).character.equals("Po") || obs_flag) {
                        i+=10;
                        break;
                    }
                    else if (!(piece_vec.get(s).character.equals("Po"))) {
                        obs_flag = true;
                        break;
                    }
                }
                else if (s+1== piece_vec.size() && obs_flag) {
                    Coordinate coordinate = new Coordinate(piece.x,i);
                    path_vec.add(coordinate);
                }

            }
        }
        obs_flag=false;
        for (int i=piece.y-1;i>=0;i--) {
            for (int s=0; s< piece_vec.size();s++) {
                if (i== piece_vec.get(s).y && piece.x== piece_vec.get(s).x) {
                    if (obs_flag && !(piece_vec.get(s).character.equals("Po")) && piece_vec.get(s).team!=piece.team) {
                        Coordinate coordinate = new Coordinate(piece.x,i);
                        path_vec.add(coordinate);
                        i-=10;
                        break;
                    }
                    else if ((piece_vec.get(s).character.equals("Po") || piece_vec.get(s).team==piece.team) && obs_flag) {
                        i-=10;
                        break;
                    }
                    else if (!(piece_vec.get(s).character.equals("Po"))) {
                        obs_flag = true;
                        break;
                    }
                }
                else if (s+1== piece_vec.size() && obs_flag) {
                    Coordinate coordinate = new Coordinate(piece.x,i);
                    path_vec.add(coordinate);
                }

            }
        }
        Vector <Coordinate> moveable = new Vector<Coordinate>();
        if ((piece.x==3 || piece.x==5)) {
            if (piece.y==0 || piece.y==2) {
                for (int i=0; i<piece_vec.size(); i++) {
                    if (piece_vec.get(i).x==4 && piece_vec.get(i).y==1) {
                        Coordinate coordinate = new Coordinate(8-piece.x,2-piece.y);
                        moveable.add(coordinate);
                    }
                }
            }
            else if (piece.y==7 || piece.y==9) {
                for (int i=0; i<piece_vec.size(); i++) {
                    if (piece_vec.get(i).x==4 && piece_vec.get(i).y==8) {
                        Coordinate coordinate = new Coordinate(8-piece.x,16-piece.y);
                        moveable.add(coordinate);
                    }
                }
            }
        }
        CheckObject(moveable);
    }

    void GetPath_Sa() {
        Coordinate coordinate;
        Vector<Coordinate> moveable_vec = new Vector<Coordinate>();
        switch (piece.x) {
            case 3:
                coordinate = new Coordinate(4,piece.y);
                moveable_vec.add(coordinate);
                break;
            case 4:
                coordinate = new Coordinate(3,piece.y);
                moveable_vec.add(coordinate);
                coordinate = new Coordinate(5,piece.y);
                moveable_vec.add(coordinate);
                break;
            case 5:
                coordinate = new Coordinate(4,piece.y);
                moveable_vec.add(coordinate);
                break;
        }
        if (piece.y==0) {
            coordinate = new Coordinate(piece.x, piece.y+1);
            moveable_vec.add(coordinate);
        }
        else if (piece.y==1) {
            coordinate = new Coordinate(piece.x, piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x, piece.y-1);
            moveable_vec.add(coordinate);
        }
        else if (piece.y==2) {
            coordinate = new Coordinate(piece.x, piece.y-1);
            moveable_vec.add(coordinate);

        }
        if (piece.x==4 && piece.y==1) {
            coordinate = new Coordinate(piece.x+1,piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x-1,piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x-1,piece.y-1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x+1,piece.y-1);
            moveable_vec.add(coordinate);
        }
        else {
            coordinate = new Coordinate(4, 1);
            moveable_vec.add(coordinate);
        }
        CheckObject(moveable_vec);


    }

    void GetPath_Jol() {
        Vector<Coordinate> moveable_vec = new Vector<Coordinate>();
        Coordinate coordinate;
        if (piece.x+1<=8) {
            coordinate = new Coordinate(piece.x+1,piece.y);
            moveable_vec.add(coordinate);
        }
        if (piece.x-1>=0) {
            coordinate = new Coordinate(piece.x-1,piece.y);
            moveable_vec.add(coordinate);
        }
        if (piece.y+1<=9) {
            coordinate = new Coordinate(piece.x,piece.y+1);
            moveable_vec.add(coordinate);
        }
        if (piece.x==4 ) {
            if (piece.y==8) {
                coordinate = new Coordinate(piece.x + 1, piece.y + 1);
                moveable_vec.add(coordinate);
                coordinate = new Coordinate(piece.x - 1, piece.y + 1);
                moveable_vec.add(coordinate);
            }
            else if (piece.y==1) {
                coordinate = new Coordinate(piece.x + 1, piece.y - 1);
                moveable_vec.add(coordinate);
                coordinate = new Coordinate(piece.x - 1, piece.y - 1);
                moveable_vec.add(coordinate);
            }
        }
        else if (piece.x==3 || piece.x==5) {
            if (piece.y==2) {
                coordinate = new Coordinate(4,1);
                moveable_vec.add(coordinate);
            }
            else if (piece.y==7) {
                coordinate = new Coordinate(4,8);
                moveable_vec.add(coordinate);
            }
        }

        CheckObject(moveable_vec);

    }

    void GetPath_King() {
        Coordinate coordinate;
        Vector<Coordinate> moveable_vec = new Vector<Coordinate>();
        switch (piece.x) {
            case 3:
                coordinate = new Coordinate(4,piece.y);
                moveable_vec.add(coordinate);
                break;
            case 4:
                coordinate = new Coordinate(3,piece.y);
                moveable_vec.add(coordinate);
                coordinate = new Coordinate(5,piece.y);
                moveable_vec.add(coordinate);
                break;
            case 5:
                coordinate = new Coordinate(4,piece.y);
                moveable_vec.add(coordinate);
                break;
        }
        if (piece.y==0) {
            coordinate = new Coordinate(piece.x, piece.y+1);
            moveable_vec.add(coordinate);
        }
        else if (piece.y==1) {
            coordinate = new Coordinate(piece.x, piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x, piece.y-1);
            moveable_vec.add(coordinate);
        }
        else if (piece.y==2) {
            coordinate = new Coordinate(piece.x, piece.y-1);
            moveable_vec.add(coordinate);

        }
        if (piece.x==4 && piece.y==1) {
            coordinate = new Coordinate(piece.x+1,piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x-1,piece.y+1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x-1,piece.y-1);
            moveable_vec.add(coordinate);
            coordinate = new Coordinate(piece.x+1,piece.y-1);
            moveable_vec.add(coordinate);
        }
        else {
            coordinate = new Coordinate(4, 1);
            moveable_vec.add(coordinate);
        }
        CheckObject(moveable_vec);


    }

    void CheckObject(Vector<Coordinate> move_able_vec) {
        Coordinate coordinate;
        for (int s=0; s<move_able_vec.size(); s++) {
            for (int i=0; i< piece_vec.size();i++) {
                if (move_able_vec.get(s).x== piece_vec.get(i).x && move_able_vec.get(s).y== piece_vec.get(i).y) {
                    if (piece.team== piece_vec.get(i).team)
                        break;
                    else {
                        coordinate = new Coordinate(move_able_vec.get(s).x,move_able_vec.get(s).y);
                        path_vec.add(coordinate);
                        break;
                    }

                }
                else if (i+1== piece_vec.size()) {
                    coordinate = new Coordinate(move_able_vec.get(s).x,move_able_vec.get(s).y);
                    path_vec.add(coordinate);
                }
            }
        }
    }
}

class Coordinate {
    int x;
    int y;
    int real_x;
    int real_y;
    int index;
    Coordinate(int _x,int _y) {
        x = _x;
        y = _y;
    }
}