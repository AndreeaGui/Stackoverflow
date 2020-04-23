import React, { Component } from "react";
import question from "../model/question";
import DumbQuestionsList from "./DumbQuestionsList";
import questionsListPresenter from "../presenter/questionsListPresenter";


const mapModelStateToComponentState = modelState => ({
    questions: modelState.questions,

});

export default class SmartFilterQuestionsByTitle extends Component {
    constructor(props) {
        super(props);
        this.state = mapModelStateToComponentState(question.state, props);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState, props));
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
                questions={question.state.filteredQuestions} />
        );
    }
}