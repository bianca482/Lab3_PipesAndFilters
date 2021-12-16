# Systemarchitekturen Lab 3 (Pipes and Filters)

com.hackoeur.jglm contains the SOURCES of the JGLM Java OpenGL Mathematics Library https://github.com/jroyalty/jglm

It is no longer maintained but has all functionality necessary for this project. Why included as source: Only Version 1.0 is on Maven Central but we need 1.0.1 for some extra features, therefore simply adding a dependency would not have worked. Providing a pre-built JAR for the students would have been problematic because this would require them to use the same Java Version as I do. Solution: simply add resources.
In general: DON'T DO THIS. However, this is a good reason for doing it.

Also I added / cleaned up functionality:
- added Vec2
- Removed warnings due to deprecated Java API usage in JglmConfig and Precision

------------------------------
## Allgemeines
Github Repository verfügbar unter: https://github.com/bianca482/Lab3_PipesAndFilters

In der Klasse Main wurde die Größe der Applikation (VIEW_WITH + VIEW_HEIGHT) leicht abgeändert. 
Zum Wechseln zwischen der Push- und Pull-Pipeline muss der Wert von USE_PUSH_PIPELINE abgeändert werden.


## Architektur
- Architektur erklären (von Push & Pull Pipeline) ->
  - Funktionsweise,
  - welche Interfaces,
  - Verschachtelung (müssen Nachfolger oder Vorgänger angegeben werden müssen)
- Screenshot beider Pipelines
- Erstellung Rotationsmatrix bei uns in ModelView Filter, nicht in render() von PipelineFactory


Bei beiden Pipelines wurde die Rotationsmatrix im ersten Filter (ModelViewTransformation) erstellt.

### Push Pipeline
<img src="resources/pictures/PushPipeline.png" alt="Push Pipeline" />

### Pull Pipeline
<img src="resources/pictures/PullPipeline.png" alt="Pull Pipeline" />
