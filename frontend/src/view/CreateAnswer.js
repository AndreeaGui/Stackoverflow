
import React from "react";

const CreateAnswer = ({ onCreate, onChange, newUserId, newQuestionId, newText }) => (
    <div className="container has-background-primary">
        <h2 className="title is-centered">Add answer</h2>
        <div>
            <label>User ID: </label>
            <input class="input" type="text" placeholder="User"
                value={newUserId} onChange={e => onChange("userId", e.target.value)} />
            <br />
            <label>Question ID: </label>
            <input class="input" type="text" placeholder="Question"
                value={newQuestionId} onChange={e => onChange("questionId", e.target.value)} />
            <br />
            <label>Text: </label>
            <input class="input" type="text" placeholder="Answer text"
                value={newText} onChange={e => onChange("text", e.target.value)} />
            <br />
            <br />
            <button class="button is-primary is-outlined is-inverted"
                onClick={onCreate}>Add Answer!</button>
            <hr></hr>
        </div>
    </div>
);

export default CreateAnswer;