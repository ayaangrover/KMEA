import kareltherobot.*;
import java.awt.Color;

/**
 * Karel follows a path of instructions to get to mine.
 * 
 * @author  Ayaan, Arjun, Aarav
 *   Team Name: Void
 *
 * @version 06/25/25
 */
public class Adventurer extends Robot
{
    /**
     * Creates a Adventurer object.
     *
     * @param street     specifies the vertical position on the grid
     *                   of this robot.
     * @param avenue     specifies the horizontal position on the grid
     *                   of this robot.
     * @param direction  specifies the direction of this robot.
     * @param beepers    specifies this robot's number of beepers.
     */
    public Adventurer (int street, int avenue, 
    Direction direction, int beepers)
    {
        super(street, avenue, direction, beepers);
    }

    /**
     * Checks if there is a beeper to the north of the robot.
     *
     * @precondition     no wall to the north of the robot
     * @postcondition     is back where it started
     */
    public boolean checkNorth()
    {
        faceNorth();
        move();
        if(nextToABeeper()){
            moveBack();
            return true;
        }
        moveBack();
        return false;
    }
    
    /**
     * Checks if there is a beeper to the east of the robot.
     *
     * @precondition     no wall to the east of the robot
     * @postcondition     is back where it started
     */
    public boolean checkEast()
    {
        faceNorth();
        turnRight();
        move();
        if(nextToABeeper()){
            moveBack();
            return true;
        }
        moveBack();
        return false;
    }
    
    /**
     * Moves back one square
     *
     * @precondition     no wall behind the robot
     * @postcondition     is back one step from where it started
     */
    public void moveBack()
    {
        turnAround();
        move();
        turnAround();
    }
    
    /**
     * Main function - runs the instructions provided for this problem.
     *
     * @precondition     no walls in the way of the beeper paths, no holes in the beeper path, beeper path is only straight(no diagonal paths), map adheres to the provided instructions for this problem
     * @postcondition     is at the end having picked up all the beepers in the mine.
     */
    public void findMine(){
        faceNorth();
        move();
        faceEast();
        move();
        
        if(checkNorth())
        {
            faceNorth();
        }
        else
        {
            faceEast();
        }
        
        while (!nextToABeeper()){
            move();
        }
                
            
        while (nextToOneBeeper()){
            followTheBeeperPath();

        }
        while (frontIsClear()){
            move();

        }
        faceEast();
        while(frontIsClear()){
            move();
        }
        
        
        findTreasure();
        
        
        if (facingNorth())
        {
            faceSouth();
        }
        else if(facingWest())
        {
            faceNorth();
        }
        else if (facingSouth() || facingEast())
        {
            faceEast();
        }
        move();
        move();
        move();
        move();
        move();
        move();
        move();
        move();
        move();
        move();
        while (!frontIsClear())
        {
            turnLeft();
        }
        
        if (leftIsClear())
        {
            while (frontIsClear())
            {
                move();
            }
        }
        else 
        {
            move();
            move();
            move();
            move();
            move();
            move();
            move();
            move();
            move();
            move();
        }
        turnRight();
        while (frontIsClear())
        {
            move();
        }
        faceEast();        
        locateMine();
        while (nextToABeeper()){
            pickBeeper();
        }
    }
    
    /**
     * Does the final mine location program
     *
     * @precondition     no walls in the robot's way
     * @postcondition     is at the final mine
     */
    public void locateMine()
    {
        while(!nextToABeeper())
        {
            faceEast();
            for(int i=0;i<4;i++)
            {
                move();
            }
            faceNorth();
            move();
        }
    }

    /**
     * Checks if the left is clear
     *
     * @precondition     none
     * @postcondition     has checked if the left is clear. same location
     */
    public boolean leftIsClear()
    {
        turnLeft();
        if (frontIsClear())
        {
            turnRight();
            return true;
        }
        turnRight();
        return false;
    }

    /**
     * Makes the robot follow the trail of beepers until it reaches the end and picks up each one.
     *
     * @preconditions robot is placed on the first beeper of the path.
     * @postconditions Robot has followed the entire path, picked up every beeper, and stopped at the end.
     */

    public void followTheBeeperPath() {
        while (!nextToTwoBeepers()) 
        {
            if (nextToABeeper()) 
            {
                pickBeeper();
                if (nextToABeeper()) 
                {
                    pickBeeper();
                }
            }

            if (frontIsClear()) 
            {
                move();
                if (nextToABeeper()) 
                {
                } 
                else 
                {
                    turnAround();
                    move();
                    turnAround();
                    tryCorners();
                }
            } 
            else 
            {
                tryCorners();
            }
        }
    }

    /**
     * Tries turning left and right to find the next beeper when the path does 
     * not continue forward and moves in the right direction
     * 
     * @preconditions robot is facing original direction, standing on a beeper, but no
     * beeper is ahead of it
     * 
     * @postconditions robot is moved one step into the new direction (left or right) 
     * if that direction it had a beeper. if those sides didn't have a beeper then robot ends facing 
     * its original direction
     */
    public void tryCorners() {
        turnLeft();
        if (frontIsClear()) 
        {
            move();
            if (nextToABeeper()) 
            {
                return;
            } 
            else 
            {
                turnAround();
                move();
                turnAround();
            }
        } 
        else 
        {
            turnRight();
        }
        turnRight();
        if (frontIsClear()) 
        {
            move();
            if (nextToABeeper()) 
            {
                return;
            } 
            else 
            {
                turnAround();
                move();
                turnAround();
            }
        }
        turnLeft();
    }

    /**
     * Checks to see if karel is on the same corner as 2 beepers
     */
    public boolean nextToTwoBeepers()
    {
        if (nextToABeeper())
        {
            pickBeeper();
            if (nextToABeeper())
            {
                putBeeper();
                return true;
            }
            putBeeper();
            return false;
        }
        return false;
    }

    /**
     * robot turns right
     */
    public void turnRight() 
    {
        turnLeft();
        turnLeft();
        turnLeft();
    }

    /**
     * robot turns 180 degrees
     */
    public void turnAround() 
    {
        turnLeft();
        turnLeft();
    }

    /**
     * Checks if the robot is next to one and only one beeper
     *
     * @precondition     none
     * @postcondition     has checked if the robot is next to one and only one beeper
     */
    public boolean nextToOneBeeper()
    {
        if (nextToABeeper())
        {
            pickBeeper();
            if (nextToABeeper())
            {
                putBeeper();
                return false;
            }
            putBeeper();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Faces north
     *
     * @precondition     none
     * @postcondition     has faced north
     */
    public void faceNorth()
    {
        while (!facingNorth())
        {
            turnLeft();
        }

    }

    /**
     * Faces south
     *
     * @precondition     none
     * @postcondition     has faced south
     */
    public void faceSouth()
    {
        while (!facingSouth())
        {
            turnLeft();
        }
    }

    /**
     * Faces east
     *
     * @precondition     none
     * @postcondition     has faced east
     */
    public void faceEast()
    {
        while(!facingEast())
        {
            turnLeft();
        }
    }

    /**
     * Goes on a "treasure hunt" and collects the beepers.
     * 
     * @precondition    No walls in the way of the robot.
     *
     * @postcondition   This robot is at the end of the treasure hunt.
     */
    public void findTreasure()
    {
        while (!HasFiveBeepers())
        {
                while (!nextToABeeper() && !HasFiveBeepers())
                {
                    move();
                }

                if (!HasFiveBeepers())
                {
                    chooseDirection();
                    //if you are not at the treasure you must be next to a beeper stack thats less than 5 (which you can use to check the direction)!
                }
        }
    }

    /**
     * Checks if the robot is next to 5 beepers(the treasure).
     * 
     * @precondition    None
     *
     * @postcondition   The robot knows if it's next to the treasure.
     */
    public boolean HasFiveBeepers()
    {
        if(nextToABeeper())
        {
            pickBeeper();
            if(nextToABeeper())
            {
                pickBeeper();
                if(nextToABeeper())
                {
                    pickBeeper();
                    if(nextToABeeper())
                    {
                        pickBeeper();
                        if(nextToABeeper())
                        {
                            putBeeper();
                            putBeeper();
                            putBeeper();
                            putBeeper();
                            System.out.println("got 5, ending!");
                            return true;
                        }
                        else
                        {
                            putBeeper();
                            putBeeper();
                            putBeeper();
                            putBeeper();

                            return false;
                        }
                    }
                    else
                    {
                        putBeeper();
                        putBeeper();
                        putBeeper();

                        return false;
                    }
                }
                else
                {
                    putBeeper();
                    putBeeper();

                    return false;
                }
            }
            else
            {
                putBeeper();

                return false;
            }
        }
        else
        {

            return false;
        }
    }

    /**
     * Chooses the direction based on the number of beepers next to it.
     * 
     * @precondition    None
     *
     * @postcondition   This robot has choosen the direction based on the number of beepers next to it and picked the beepers up.
     */
    public void chooseDirection()
    {
        faceEast();
        while (nextToABeeper())
        {
            pickBeeper();
            turnLeft();
        }
    }

}