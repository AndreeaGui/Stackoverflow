import React, { Component } from "react";
import question from "../model/question";
import questionsListPresenter from "../presenter/questionsListPresenter";
import DumbQuestionsList from "./DumbQuestionsList";



const mapModelStateToComponentState = modelState => ({
    questions: modelState.filteredQuestions,

});

export default class SmartFilterQuestionsByTag extends Component {
    constructor() {
        super();
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
            <DumbQuestionsList
                onViewTags={questionsListPresenter.onViewTags}
                questions={this.state.questions} />
        );
    }
}