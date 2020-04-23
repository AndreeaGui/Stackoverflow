import question from "../model/question";

class CreateQuestionPresenter {

    onCreate() {
        question.addQuestion(question.state.newQuestion.title,
            question.state.newQuestion.text, question.state.newQuestion.tags.split(","));
        question.changeNewQuestionProperty("userId", "");
        question.changeNewQuestionProperty("title", "");
        question.changeNewQuestionProperty("text", "");
        question.changeNewQuestionProperty("tags", []);
        window.location.assign("#/");
    }

    onChange(property, value) {
        question.changeNewQuestionProperty(property, value);
    }

}

const createQuestionPresenter = new CreateQuestionPresenter();

export default createQuestionPresenter;