mocktopus
=========

This thing isn't done yet, don't look at it!


todo:

add a "real" api (maybe a weather api?)

improve config activity

add api passthrough (so some methods are mocked, some make real requests)
add more object support (support filling out Integer, Double, etc)
add primitives support (just use objects for now?)
add Map support (not uncommon with gson)
add recursive child limits (if MyObject contains a MyObject, tries to add more forever. depth limit?)
generics support - in progress.
add observable support (for better retrofit support)
    observable options?
        delay
        indefinite loading

add callback support (for better retrofit support)

things to consider:
instead of filling a list with a bunch of the same items, build the list with all the different items?
    maybe have an @List(MyListBuilder.class) that makes a custom list instead of trying to do it automatically?

more annotations?
add way to set list of values to use for annotations (like to say StringImageUrl should use X,Y,and Z instead of its defaults)


add some flavors to show how to use next to a "real" api

long-term to-do
code generation instead of runtime?