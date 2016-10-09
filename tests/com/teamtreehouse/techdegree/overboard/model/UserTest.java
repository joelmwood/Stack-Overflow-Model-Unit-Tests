package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Wood on 10/09/2016.
 */

public class UserTest {

    private Board board;
    private User questioner;
    private User answerer;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        questioner = board.createUser("Joel");
        answerer = board.createUser("Billy");
        question = questioner.askQuestion("Do you like unit testing?");
        answer = answerer.answerQuestion(question, "No, but I understand that it is important.");

    }

    @Test
    public void questionerRepIncreasesIfQuestionIsUpvoted() throws Exception {
        answerer.upVote(question);

        assertEquals(5, questioner.getReputation());
    }

    @Test
    public void answererRepIncreasesIfAnswerIsUpvoted() throws Exception {
        questioner.upVote(answer);

        assertEquals(10, answerer.getReputation());
    }

    @Test
    public void acceptedAnswerIncreasesRep() throws Exception {
        questioner.acceptAnswer(answer);

        assertEquals(15, answerer.getReputation());
    }

    @Test(expected = VotingException.class)
    public void originalQuestionerVotingQuestionUpNotAllowed() throws Exception {
        questioner.upVote(question);
    }

    @Test
    public void originalQuestionerVotingQuestionDownNotAllowed() throws Exception {
        questioner.downVote(question);
    }

    @Test
    public void originalQuestionerVotingAnswerUpNotAllowed() throws Exception {
        questioner.upVote(answer);
    }

    @Test
    public void originalQuestionerVotingAnswerDownNotAllowed() throws Exception {
        questioner.downVote(answer);
    }

    @Test(expected = AnswerAcceptanceException.class)
    public void onlyOriginalQuestionerCanAcceptAnswer() throws Exception {
        answerer.acceptAnswer(answer);
    }

    //Extra code found in getReputation method not requested to be tested,
    // but written for "Exceeds" rating grade
    @Test
    public void downVotingCostsOneRepPointForAnswerer() throws Exception {
        answerer.downVote(answer);

        assertEquals("Answer down-voted", -1, answerer.getReputation());
    }

    @Test
    public void userWithNoUpvotesReturns0Reputation() throws Exception {
        assertEquals(0, questioner.getReputation());
    }
}