package com.dungeonsanddishes.game;

import java.util.Random;

public class DungeonGenerator {

    private Random random = new Random();

    private boolean[][] create2DArray(int dimensions){
        boolean [][] arr = new boolean[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++)
        {
            for (int j = 0; j < dimensions; j++)
            {
                arr[i][j] = false;
            }
        }
        return arr;
    }

    public boolean[][] createDungeonMap(){
        int dimensions = 20;
        int maxTunnels = 5;
        int maxLength = 8;

        // create dimensions x dimensions 2D-array
        boolean [][] dungeonMap = create2DArray(dimensions);

        // place the random walker at a random cell
        int currentRow = random.nextInt(dimensions-1);
        int currentColumn = random.nextInt(dimensions-1);

        int [][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int [] lastDirection = {0, 0};
        int [] randomDirection = {0, 0};

        while(dimensions > 0 && maxTunnels > 0 && maxLength > 0)
        {
            // set a direction for random walker
            // direction should be perpendicular to last direction
            do{
                randomDirection = directions[random.nextInt(directions.length)];
            } while((randomDirection[0] == -lastDirection[0] && randomDirection[1] == -lastDirection[1])
                    || (randomDirection[0] == lastDirection[0] && randomDirection[1] == lastDirection[1]));

            // set random length
            int randomLength = random.nextInt(dimensions);
            int tunnelLength = 0;

            // let random walker walk the random length
            while(tunnelLength < randomLength)
            {
                // stop if random walker is going beyond array
                if((currentRow == 0 && randomDirection[0] == -1) ||
                        (currentColumn == 0 && randomDirection[1] == -1) ||
                        ((currentRow == dimensions - 1 && randomDirection[0] == 1)) ||
                        ((currentColumn == dimensions - 1 && randomDirection[1] == 1)))
                {
                    break;
                }
                // set cell where random walker is to true
                // and prepare random walker to walk to next cell
                else {
                    dungeonMap[currentRow][currentColumn] = true;
                    currentRow += randomDirection[0];
                    currentColumn += randomDirection[1];
                    tunnelLength++;
                }
            }

            // if randomLength was set to zero, don't update variables
            if(tunnelLength > 0)
            {
                lastDirection = randomDirection;
                maxTunnels--;
            }
        }
        return dungeonMap;

        /*
        - = false
        T = true;

        dungeonMap =   [[-,-,-,-,-,-,-,-,-,-,-,-,-],
                        [-,-,-,-,-,-,-,T,-,-,-,-,-],
                        [-,-,-,-,-,-,-,T,-,-,-,-,-],
                        [-,-,-,-,-,-,-,T,T,-,-,-,-],
                        [-,-,-,-,-,-,-,-,T,T,T,-,-],
                        [-,-,-,-,T,T,T,-,-,-,T,-,-],
                        [-,-,-,-,T,T,T,-,T,T,T,-,-],
                        [-,-,-,-,T,-,-,-,T,-,-,-,-],
                        [-,-,-,-,T,-,-,-,T,T,-,-,-],
                        [-,-,-,-,T,T,T,T,T,T,-,-,-],
                        [-,-,-,-,-,-,-,-,-,-,-,-,-]]
        * */
    }
}
