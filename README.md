**Spécifications des services REST :**

## Produit

| **URI** | **Méthode** | **Auth?** | **Action** |
| --- | --- | --- | --- |
| **vehicles** | GET | Non | READ ALL : retourne tous les véhicules de la db |
| **vehicles?category=value** | GET | Non | READ ALL FILTERED : retourne tous les véhicules dont la catégorie est &#39;value&#39; |
| **vehicles** | POST | Oui | CREATE ONE : ajoute un véhicule dans la db avec les données passées dans la requête |
| **vehicles/{id}** | DELETE | Oui | DELETE ONE : supprime un véhicule de la db dont l&#39;id est &#39;id&#39; |
| **vehicles/{id}** | PUT | Oui | UPDATE ONE : Met à jour les informations d&#39;un véhicule de la db dont l&#39;id est &#39;id&#39; |
| **vehicles/{id}** | GET | Non | READ ONE : retourne un véhicule de la db dont l&#39;id est &#39;id&#39;. |
| **vehicles?minPrice=minPrice&amp;maxPrice=maxPrice** | GET | Non | READ ALL FILTERED : retourne tous les véhicules dont le prix situé entre minPrice et maxPrice |


## Commentaires clients

| **URI** | **Méthode** | **Auth?** || **Action** |
| --- | --- | --- | --- | --- |
| **comments/user/{user\_id}** | GET | Non | Non | READ ALL :retourne tous les commentaires relatifs à un utilisateur dont l&#39;id = {id}, ordonné par date de création |
| **comments/vehicle/{vehicle\_id}** | GET | Non | Non | READ ALL :retourne tous les commentaires relatifs à un véhicule dont l&#39;id = {id}, ordonné par date de création |
| **comments/vehicle/{vehicle\_id}/withuser/{user\_id}** | GET | Non | Non | READ ALL :retourne tous les commentaires relatifs à un véhicule dont l&#39;id = {vehicle\_id} et à un utilisateur dont l&#39;id = {user\_id}, ordonné par date de création |
| **comments/vehicle/{vehicle\_id}/exceptuser/{user\_id}** | GET | Non | Non | READ ALL :retourne tous les commentaires relatifs à un véhicule dont l&#39;id = {vehicle\_id} et ne provenant pas de l&#39;utilisateur dont l&#39;id = {user\_id}, ordonné par date de création |
| **comments/** | POST | Oui | Non | CREATE ONE :ajoute un commentaire à la DB avec les données passées dans la requête |
| **comments/** | PUT | Oui | Oui si pas le créateur du commentaire | UPDATE ONE:mets à jour un commentaire avec les données passées dans la requête (texte et état [SUPPRIME,VALIDE]) |

## Panier

| **URI** | **Méthode** | **Auth?** | **Admin?** | **Action** |
| --- | --- | --- | --- | --- |
| **cart/{idUser}** | GET | Oui | Non | READ ALL : retourne tous les véhicules contenus dans le panier de l&#39;utilisateur ayant comme id {idUser}|
| **cart/{id}** | POST | Oui | Non | CREATE ONE : ajoute un véhicule au panier ayant comme id l&#39;id passé |
| **cart/{id}** | DELETE | Oui | Non | DELETE ONE : supprime un véhicule du panier ayant comme id l&#39;id passé |
| **cart/{id}** | PUT | Oui | Non | UPDATE ONE : Met à jour la quantité du véhicule du panier ayant comme id l&#39;id, avec une nouvelle quantité, passée dans le body de la requête |
| **cart/deleteAll/{idUser}** | DELETE | Oui | Non | DELETE ALL FOR ONE USER : supprime tous les paniers de l'utilisateur dont l'id est {idUser} |

## Utilisateurs

| **URI** | **Méthode** | **Auth ?** | **Admin ?** | **Action** |
| --- | --- | --- | --- | --- |
| **users** | GET | Non | Non | READ ALL : récupère tous les utilisateurs |
| **users/{id}** | GET | Non | Non | READ ONE : récupère l&#39;utilisateur ayant l&#39;id passé dans l&#39;url |
| **users/email/{email}** | GET | Non | Non | READ ONE : récupère l&#39;utilisateur avec l&#39;adresse mail passé en paramètre de l&#39;url |
| **users** | POST | Non | Non | CREATE ONE : ajoute un nouvel utilisateur avec les données du corps de la requête |
| **users/{id}** | PUT | Oui | Non | UPDATE ONE : modifie l&#39;utilisateur ayant l&#39;id passé en paramètre avec les données du corps de la requête |
| **users/{id}** | DELETE | Oui | Oui | DELETE ONE : supprime l&#39;utilisateur avec l&#39;id passé en paramètre |
