truncate table ELMAH_error
go
truncate table images
go
delete offer
go
delete product
go
delete geoRequest
go
delete occasion
go
delete userCompany
go
delete store
go
delete [state]
go
delete companySubscription
go
delete company
go
delete webpages_UsersInRoles
go
delete UserProfile
go
delete subscription
go
delete [profile]
go

--update webpages_Membership set IsConfirmed=1 where UserId=11
--update UserProfile set Active=1 where UserId=23
--insert into webpages_UsersInRoles values (23,1)
