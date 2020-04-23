import answer from "../model/answer";


class AnswerPresenter {

    onCreate() {
        answer.addAnswer(answer.state.newAnswer.questionId, answer.state.newAnswer.text);
        answer.changeNewAnswerProperty("answerId", "");
        answer.changeNewAnswerProperty("userId", "");
        answer.changeNewAnswerProperty("questionId", "");
        answer.changeNewAnswerProperty("text", "");

    }

    onChange(property, value) {
        answer.changeNewAnswerProperty(property, value);
    }
    onAddAnswer() {
        window.location.assign("#/create-answer");
    }

    onDelete = (answerId) => {
        answer.deleteAnswer(answerId);

    }

    onUpdate = (answerId, answerUpdateText) => {
        answer.updateAnswer(answerId, answerUpdateText);
    }


    refreshQuestionAnswers(id){
        answer.listAnswersByQuestion(id);
    }

    onListAnswersByScore(questinId){
        answer.listAnswersByScore(questinId); 
    }
}

const answerPresenter = new AnswerPresenter();

export default answerPresenter;