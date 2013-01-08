Gnieh Pretty Printer
====================

Pretty Printer library based on the well-known Philip Wadler's "Prettier Printer".

The API was inspired by the Haskell [`Text.PrettyPrint.Leijen`](http://hackage.haskell.org/packages/archive/wl-pprint/1.0/doc/html/Text-PrettyPrint-Leijen.html) module.

Example
-------

Let's say you want to render some Scala code, formatted in several ways. For example your application generates Scala code and you want to output it so that human beings can read it easily, or sometimes so that it is as concise as possible.

After building your code document, you end up with this:
```scala
val myDoc = "def" :+: "invert" :: group("(" :: align("toInvert:" :+: "Int," :|:
  "shouldI:" :+: "Boolean" :: ")")) :+: nest(2)("=" :|:
    group(nest(2)("if" :+: "(shouldI)" :|: "toInvert") :|:
    nest(2)("else" :|: "-toInvert")))
```

you can render it on 80 columns with proper indentation, which will produce the following output:
```
def invert(toInvert: Int, shouldI: Boolean) =
  if (shouldI) toInvert else -toInvert
```

but you can decide to render it on 10 columns still with proper indentation, which produces:
```
def invert(toInvert: Int,
           shouldI: Boolean) =
  if (shouldI)
    toInvert
  else
    -toInvert
```

Nice, isnt't it?

another option is to discard indentation and groups, which produces some valid Scala code, yet harder to read for people:
```
def invert(toInvert: Int,
shouldI: Boolean) =
if (shouldI)
toInvert
else
-toInvert
```

Documentation
-------------

You can find the API documentation and all operators on document in the (generated)[http://gnieh.github.com/gnieh-pp/api/#package]
