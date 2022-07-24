Annotation processor that generates code to serialize/deserialize domain objects to/from byte arrays using FlatBuffers and a demo module that shows how annotation processor works.

For this to work FlatBuffers compiler (flatc) must be installed.

Assumptions:
* FlatBuffers classes have a prefix Fb
* all fields in domain classes are present in FlatBuffers classes and are named identically
