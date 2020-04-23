
import React from "react";

const CreateQuestion = ({ onCreate, onChange, newUserId, newTitle, newText, newTags }) => (
    <div className="container has-background-primary">
        <h2 className="title">Add question</h2>
        <div>
            <label>User ID: </label>
            <input class="input" type="text" placeholder="User"
                value={newUserId} onChange={e => onChange("userId", e.target.value)} />
            <br />
            <label>Title: </label>
            <input class="input" type="text" placeholder="Question title"
                value={newTitle} data-cy = "Title"
                onChange={e => onChange("title", e.target.value)} />
            <br />
            <label>Text: </label>
            <input class="input" type="text" placeholder="Question text"
                value={newText} data-cy = "Text"
                onChange={e => onChange("text", e.target.value)} />
            <br />
            <label>Tags: </label>
            <input class="input" type="text" placeholder="Tags separated by ,"
                value={newTags} data-cy = "Tags"
                onChange={e => onChange("tags", e.target.value)} />
            <br />
            <br />
            <button class="button is-primary is-outlined is-inverted"
                onClick={onCreate} data-cy = "create question">Add Question!</button>
            <hr></hr>
        </div>
    </div>
);

export default CreateQuestion;