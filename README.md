mocktopus
=========

This thing is approaching usable. You can look at it now

how to use

1. annotate your model objects
2. inject mocktopus service in place of real service
3. start your app and change stuff in the config screen
4. see results!


todo:
-----

* expand on demo reddit api
    * story detail
    * user detail
* improve demo 
    * show what a "real" consumer activity would look like
    * move json viewer into another activity
* improve config activity
    * show currently selected value
* improve configuration
    * method options
        * error/success 
    * observable options
        * delay
        * indefinite loading?

* add primitives support (support int, not just Integer)
* add recursive child limits (if MyObject contains a MyObject, tries to add more forever. depth limit?)
* add callback support (for better retrofit support)
* more annotations?
    * string
    * int
* list builder options/settings?
* add some flavors to show how to use next to a "real" api

        
* add api passthrough (so some methods are mocked, some make real requests)

api passthrough should be an api level setting, it has its own "settings" that adds a new default, "passthrough"

instead of an object builder, you use an object modifier that iterates through 
all the fields in the response and modifies them to be the values set in the settings object, 
ignoring fields set to "passthrough." This would make working with recursive objects easier, 
as well as providing more realistic data.
