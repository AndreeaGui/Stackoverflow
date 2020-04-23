import question from "../model/question";



class QuestionsListPresenter {
    refreshQuestionList(){
        question.loadAllQuestions();
    }

    onCreateQuestion() {
        window.location.assign("#/create-question");
    }

    onViewTags(index) {
        window.location.assign("#/question-details/" + index);
    }

    onSearch(searchWord) {
        question.changeSearch(searchWord);

    }

    onFilterByTag() {
        question.filterByTag(question.state.search);
        question.changeSearch("");//the search is reset to empty
    }

    onFilterByTitle() {
        question.filterByTitle(question.state.search);
        question.changeSearch("");
    }

}

const questionsListPresenter = new QuestionsListPresenter();

export default questionsListPresenter;