/*
Many-to-one unidireccional
    Sólo se representa la asociación en el lado muchos.
    Se usa la anotación @ManyToOne
    Podemos añadir @JoinColumn para indicar el nombre de la columna que será la clave externa.
    También podemos añadir  en el joincolumn @ForeignKey(name= ·fk_publicacion_usuario") para indicar el nombre de la restricción que se creará a nivel de base de datos.

One-to-many unidireccional
    Representa la asociación en el lado uno.
    Por eso, en la clase del lado uno debemos colocar una colección de elementos que mapean los elementos asociados en la otra clase.
    La colección puede ser un List o un Set.
    Si la asociación @OneToMany no tiene la opuesta asociación @ManyToOne será unidireccional. En caso de que sí exista será bidireccional.
    En la entidad del lado muchos no hay que hacer nada.
    DENTRO DEL ONE TO MANy(
        cascade = CascadeType.ALL propagará (en cascada) todas las operaciones a las entidades asociadas.
        orphanRemoval = true indica que la entidad hija será borrada cuando se borre la madre. A false, la hija queda desvinculada.

One-to-many bidireccional
    en el lado muchos (Publicacion) se agregará un atributo usuario anotado con @ManyToOne. En el lado uno (Usuario) se agregará un atributo colección de publicaciones
    anotado con @OneToMany que usa la propiedad mappedBy apuntando al atributo relacionado en Publicacion.
    ONE TO MANY referencia al atributo del otro lado mediante la propiedad mappedBy = "usuario"

One-to-one unidireccional
    hay que decidir un lado como propietario

One-to-one bidireccional
    Para este caso, visto el esquema de las tablas, queremos que la clave ajena usuario_id en perfiles referencie a la PK id de usuarios y que,
    además, usuario_id sea PK en perfiles. foreingkey
    En esas condiciones la entidad Perfil sería (ojo, anotación @MapsId)
    En el lado Usuario tendremos que tener un atributo perfil relacionado con el atributo usuario de la entidad Perfil.

Many-to-many unidireccional
    necesita una tabla que realice de enlace entre dos entidades (publicaciones_tags)
    manytomany(cascade = ...)
    jointable(name=nombretbala,joincolumn=@joincoulumn(), inversejoincolumns = ...

Many-to-many bidireccional
    tag tendrá que incluir un atributo colección de publicaciones con la relación ManyToMany.
    En Publicacion tendríamos el mismo código que en la asociación unidireccional anteriormente vista. Deberá incluir los métodos addTag y removeTag



CascadeType.PERSIST
Propaga la operación persistir (guardar) de la entidad principal a las entidades relacionadas.

CascadeType.MERGE
Propaga la operación merge (actualización) de la entidad principal a las entidades relacionadas.

CascadeType.REMOVE
Propaga la operación remove (eliminación) de la entidad principal a las entidades relacionadas.

CascadeType.REFRESH
Propaga la operación de actualización (refrescar) de la entidad principal a las entidades relacionadas, sincronizando su estado con el de la base de datos.

CascadeType.DETACH
Propaga la operación detach (desasociar del contexto de persistencia) de la entidad principal a las entidades relacionadas.

CascadeType.ALL
Combina todos los tipos de cascada anteriores ( PERSIST, MERGE, REMOVE, REFRESH, DETACH).



Cuando se utiliza FetchType.LAZY, los datos asociados no se obtienen inmediatamente cuando se carga la entidad principal.
 las entidades realcionadas solo se cargarán cuando se acceda a ellas explícitamente en el código.

Esto contrasta con FetchType.EAGER, donde las entidades relacionadas se obtienen inmediatamente junto con la entidad principal.



@GeneratedValue(strategy = GenerationType.SEQUENCE):
Indica que la clave principal debe generarse utilizando una secuencia de base de datos.



Si no queremos que se puedan repetir los elementos en la tabla intermedia utilizaremos un Set
Si puede haber repetidos podemos usar un List.
 */