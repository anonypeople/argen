package unalcol.agents.examples.sudoku.naive;

import unalcol.agents.search.GoalTest;
import unalcol.agents.search.State;

/**
 * <p>Title: </p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gómez
 * @version 1.0
 */
public class NaiveSudokuGoalTest implements GoalTest {
    public NaiveSudokuGoalTest() {
    }

    public boolean test(State state) {
        NaiveSudokuBoardState sudoku_state = (NaiveSudokuBoardState) state;
        return sudoku_state.board.solved();
    }
}
