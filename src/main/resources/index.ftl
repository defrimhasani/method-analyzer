<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report</title>
    <!-- CSS only -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fontawesome.com/v3.2.1/assets/font-awesome/css/font-awesome.css" >
</head>

<body>
<div class="row">
    <div class="col">
        <h1>Analiza e kodit</h1>
        <br>
        <dl>
            <dt>Metoda</dt>
            <dd>${methodName}</dd>

            <dt>Numri Rreshtave</dt>
            <dd>${nrOfLines}</dd>

            <dt>Numri Parametrave</dt>
            <dd>${numberOfParameters}</dd>

            <dt>Kompleksiteti ciklomatik</dt>
            <dd>${cyclomaticComplexity}</dd>

            <dt>Numri i kushteve te varura nga parametrat hyres</dt>
            <dd>${numberOfConditionsDependentOnParameters}</dd>

            <dt>Numri minimal i test caseve</dt>
            <dd>${minTestCases}</dd>

            <dt>Numri maksimal i test cases</dt>
            <dd>${maxTestCases}</dd>


        </dl>
    </div>
    <div class="col">
        <pre>${methodRaw}</pre>
    </div>
</div>
<br/>
<h3>Disa vlera per parametrat hyres</h3>
<table class="table">
    <tbody>

    <#list inputParameterData as inputParameter >
        <tr>
            <th scope="row">${inputParameter.parameter}</th>
            <th>${inputParameter.examples}</th>
        </tr>

    </#list>

    </tbody>
</table>
<br/>
<h3>Analiza e kushteve</h3>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Blloku</th>
        <th scope="col">Kushti</th>
        <th scope="col">Variabla</th>
        <th scope="col">Skenari Pozitiv</th>
        <th scope="col">Skenari Negativ</th>
    </tr>
    </thead>
    <tbody>
    <#list conditionData as data>

        <tr>
            <th scope="row">${data_index +1}</th>
            <td>${data.statement}</td>
            <td>${data.condition}</td>
            <td>${data.variable}</td>
            <td>${data.positiveValue}</td>
            <td>${data.negativeValue}</td>
        </tr>
    </#list>

    </tbody>
</table>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>


</body>

</html>