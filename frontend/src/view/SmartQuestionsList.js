import React, { Component } from "react";
import QuestionsList from "./QuestionsList";
import question from "../model/question";
import questionsListPresenter from "../presenter/questionsListPresenter";



const mapModelStateToComponentState = modelState => ({
    questions: modelState.questions,

});

export default class SmartQuestionsList extends Component {
    constructor() {
        super();
        questionsListPresenter.refreshQuestionList();
        this.state = mapModelStateToComponentState(question.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        question.addListener("change", this.listener);
    }

    componentWillUnmount() {
        question.removeListener("change", this.listener);
        //prevent memory leacks
    }

    //the render method - shows the things
    render() {
        return (
            <QuestionsList
                onViewTags={questionsListPresenter.onViewTags}
                onCreateQuestion={questionsListPresenter.onCreateQuestion}
                onFilterByTag={questionsListPresenter.onFilterByTag}
                onFilterByTitle={questionsListPresenter.onFilterByTitle}
                questions={this.state.questions}
                search={this.state.search}
                onSearch={questionsListPresenter.onSearch} />
        );
    }
}