package logic.analyzer.complexity;


import static logic.analyzer.AnalyzerTask.Severity.INFORMATION;
import static logic.analyzer.AnalyzerTask.Task.CALCULATE_CYCLOMATIC_COMPLEXITY;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.WhileStmt;
import logic.analyzer.AnalyzerTask;
import logic.analyzer.utils.IfStatementConditionUtils;

/***
 * REF : https://perso.ensta-paris.fr/~diam/java/online/notes-java/principles_and_practices/complexity/complexity-java-method.html
 */
public class ComplexityAnalyzer extends AnalyzerTask
{
	private final ComplexityCounter counter;

	public ComplexityAnalyzer()
	{
		counter = new ComplexityCounter();
	}

	public int findComplexity(final MethodDeclaration result) throws Exception
	{
		log(CALCULATE_CYCLOMATIC_COMPLEXITY, INFORMATION, "Analyzing cyclomatic complexity for method {0}", result.getName().asString());

		counter.reset();
		// Start with a count of one for the method.
		counter.increase();

		NodeList<Statement> statements = result
				.getBody()
				.orElseThrow(() -> new Exception("Something went wrong while parsing method body"))
				.getStatements();

		statements.forEach(statement -> processBlock(statement, counter, result));

		return counter.getValue();
	}


	private void processBlock(Statement statement, ComplexityCounter counter, MethodDeclaration methodDeclaration)
	{
		if (statement.isIfStmt())
		{
			IfStmt ifStmt = statement.asIfStmt();

			processIfStatement(ifStmt, counter);

			Statement thenStmt = ifStmt.getThenStmt();

			if (thenStmt != null && thenStmt.isBlockStmt())
			{
				log(CALCULATE_CYCLOMATIC_COMPLEXITY,
						INFORMATION,
						"Analyzing inner block for statement {0}", ifStmt.getCondition());

				NodeList<Statement> statements = thenStmt.asBlockStmt().getStatements();

				log(CALCULATE_CYCLOMATIC_COMPLEXITY,
						INFORMATION,
						"Inner block for statement {0} contains {1} statements", ifStmt.getCondition(), statements.size());

				statements.forEach(innerStatement -> processBlock(innerStatement, counter, methodDeclaration));

			}
		}

		if (statement.isForStmt() || statement.isWhileStmt() || statement.isDoStmt() || statement.isBreakStmt() || statement.isContinueStmt())
		{
			log(CALCULATE_CYCLOMATIC_COMPLEXITY,
					INFORMATION,
					"Statement is one a for loop, while loop, do while loop, break or continue statement so increase counter for 1");

			counter.increase();

		}

		if (statement.isForStmt())
		{
			ForStmt forStmt = statement.asForStmt();

			Statement body = forStmt.getBody();

			if (body != null && body.isBlockStmt())
			{
				NodeList<Statement> statements = body.asBlockStmt().getStatements();

				log(CALCULATE_CYCLOMATIC_COMPLEXITY,
						INFORMATION,
						"Analyzing inner block for 'For loop' statement which contains {0} statements", statements.size());

				for (Statement innerStatement : statements)
				{
					processBlock(innerStatement, counter, methodDeclaration);
				}

			}
		}

		if (statement.isDoStmt())
		{
			DoStmt doStmt = statement.asDoStmt();

			Statement body = doStmt.getBody();

			if (body != null && body.isBlockStmt())
			{
				NodeList<Statement> statements = body.asBlockStmt().getStatements();

				log(CALCULATE_CYCLOMATIC_COMPLEXITY,
						INFORMATION,
						"Analyzing inner block for 'Do while loop' statement which contains {0} statements", statements.size());

				for (Statement innerStatement : statements)
				{
					processBlock(innerStatement, counter, methodDeclaration);
				}

			}
		}

		if (statement.isWhileStmt())
		{
			WhileStmt whileStmt = statement.asWhileStmt();

			Statement body = whileStmt.getBody();

			if (body != null && body.isBlockStmt())
			{
				NodeList<Statement> statements = body.asBlockStmt().getStatements();

				log(CALCULATE_CYCLOMATIC_COMPLEXITY,
						INFORMATION,
						"Analyzing inner block for 'While loop' statement which contains {0} statements", statements.size());

				for (Statement innerStatement : statements)
				{
					processBlock(innerStatement, counter, methodDeclaration);
				}

			}
		}

		if (statement.isReturnStmt())
		{
			log(CALCULATE_CYCLOMATIC_COMPLEXITY,
					INFORMATION,
					"Found return statement, checking if this return statement is the last statement");

			if (!methodDeclaration.getBody().get().getStatements().getLast().get().equals(statement))
			{
				log(CALCULATE_CYCLOMATIC_COMPLEXITY,
						INFORMATION,
						"{0} is not the last statement so increasing counter for 1", statement.asReturnStmt().toString());

				counter.increase();
			}
		}

		if (statement.isSwitchStmt())
		{
			NodeList<SwitchEntry> entries = statement.asSwitchStmt()
					.getEntries();

			log(CALCULATE_CYCLOMATIC_COMPLEXITY,
					INFORMATION,
					"Switch statement has {0} entries so increasing counter for {0}", entries.size());

			counter.increase(entries.size());

		}
	}


	private void processIfStatement(final IfStmt asIfStmt, ComplexityCounter counter)
	{
		Expression condition = asIfStmt.getCondition();

		log(CALCULATE_CYCLOMATIC_COMPLEXITY,
				INFORMATION,
				"Calculating cc for if statement {0}",
				condition);

		if (condition.isBinaryExpr())
		{
			int numberOfExpressions = IfStatementConditionUtils.getNumberOfExpressions(condition.asBinaryExpr());

			log(CALCULATE_CYCLOMATIC_COMPLEXITY,
					INFORMATION,
					"If statement is a binary expression and has {0} expressions. Increasing counter for {0}",
					numberOfExpressions);

			counter.increase(numberOfExpressions);
		}

		// e.g s.startsWith();
		if (condition.isMethodCallExpr())
		{
			log(CALCULATE_CYCLOMATIC_COMPLEXITY,
					INFORMATION,
					"If statement condition is method call expression. Increasing counter for 1");

			counter.increase();
		}


	}


}


