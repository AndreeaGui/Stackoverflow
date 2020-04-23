
import React from "react";
import DumbQuestionsList from "./DumbQuestionsList";

const QuestionsList = ({ questions, onCreateQuestion, onViewTags, onFilterByTag, onFilterByTitle,
    search, onSearch }) => (
        <div className="container has-background-primary">
            <h2 className="title is-1 is-centerd">Questions</h2>
            <br /> <br />
            <label className="subtitle is-4">Search: </label>
            <input className="input is-medium" type="text" placeholder="Title/Tag"
                value={search} data-cy="find title"
                onChange={e => onSearch(e.target.value)}
            />
            <br />
            <button className="button is-light is-outlined"
                onClick={onFilterByTag}>Filter questions by tag</button>
            <br />
            <button className="button is-light is-outlined" data-cy="find"
                onClick={onFilterByTitle}>Filter questions by title</button>
            <br /> <br />
            < DumbQuestionsList
                onViewTags={onViewTags}
                questions={questions} />
            <br />
            <button className="button is-light is-outlined"
                onClick={onCreateQuestion} data-cy="add question">Add new question</button>


        </div>
    );

export default QuestionsList;