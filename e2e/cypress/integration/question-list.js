describe("The question list ", function(){

    it("should show 3 questions", function(){
        cy.visit("/");

        cy.get('[data-cy="question"]').should("have.length", 3);
    })

    it("should add a question", function(){
        cy.visit("/");

        cy.get('[data-cy="add question"]').click();
        cy.get('[data-cy="Title"]').type("cypress title");
        cy.get('[data-cy="Text"]').type("cypress text");
        cy.get('[data-cy="Tags"]').type("cypress, title, text");
        cy.get('[data-cy="create question"]').click();

        cy.get('[data-cy="question"]')
        .should("have.length", 4)
        .and("contain", "cypress");
    })

    it("should find title one and delete its answer", function(){
        cy.visit("/");
        cy.get('[data-cy="find title"]').type("three");
        cy.get('[data-cy="find"]').click();
        cy.get('[data-cy="question"]').should("have.length", 1);

        cy.get('[data-cy="show details"]').click();
        cy.get('[data-cy="answer"]').should("have.length", 1);

        cy.get('[data-cy="delete answer"]').click();
        cy.get('[data-cy="answer"]').should("have.length", 0);

    })


})