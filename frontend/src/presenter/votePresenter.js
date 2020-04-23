import answer from "../model/answer";
import question from "../model/question";

class VotePresenter {

    onQuestionVoteUp = (questionIndex) => {
        question.addQuestionVote(questionIndex, true);
        question.loadAllQuestions();
    }

    onQuestionVoteDown = (questionIndex) => {
        question.addQuestionVote(questionIndex, false);
        question.loadAllQuestions();
    }

    onAnswerVoteUp = (questionId, answerId) => {
        answer.addAnswerVote(questionId, answerId, true);
        answer.listAnswersByQuestion(questionId);
    }

    onAnswerVoteDown = (questionId, answerId) => {
        answer.addAnswerVote(questionId, answerId, false);
        answer.listAnswersByQuestion(questionId);
    }

}

const votePresenter = new VotePresenter();

export default votePresenter;