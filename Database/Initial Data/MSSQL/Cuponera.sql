USE [master]
GO
/****** Object:  Database [Cuponera]    Script Date: 10/13/2014 23:09:23 ******/
CREATE DATABASE [Cuponera] ON  PRIMARY 
( NAME = N'Cuponera', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.SQL2008\MSSQL\DATA\Cuponera.mdf' , SIZE = 2304KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'Cuponera_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.SQL2008\MSSQL\DATA\Cuponera_log.LDF' , SIZE = 832KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [Cuponera] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Cuponera].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Cuponera] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [Cuponera] SET ANSI_NULLS OFF
GO
ALTER DATABASE [Cuponera] SET ANSI_PADDING OFF
GO
ALTER DATABASE [Cuponera] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [Cuponera] SET ARITHABORT OFF
GO
ALTER DATABASE [Cuponera] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [Cuponera] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [Cuponera] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [Cuponera] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [Cuponera] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [Cuponera] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [Cuponera] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [Cuponera] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [Cuponera] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [Cuponera] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [Cuponera] SET  ENABLE_BROKER
GO
ALTER DATABASE [Cuponera] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [Cuponera] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [Cuponera] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [Cuponera] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [Cuponera] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [Cuponera] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [Cuponera] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [Cuponera] SET  READ_WRITE
GO
ALTER DATABASE [Cuponera] SET RECOVERY FULL
GO
ALTER DATABASE [Cuponera] SET  MULTI_USER
GO
ALTER DATABASE [Cuponera] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [Cuponera] SET DB_CHAINING OFF
GO
USE [Cuponera]
GO
/****** Object:  Table [dbo].[profilegroups]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[profilegroups](
	[IdProfileGroup] [int] IDENTITY(17,1) NOT NULL,
	[Name] [nvarchar](45) NULL,
	[IsFixed] [int] NULL,
	[AppVersion] [nvarchar](45) NULL,
	[Geolocation] [int] NULL,
	[PushNotification] [int] NULL,
	[SMSNotification] [int] NULL,
	[EmailNotification] [int] NULL,
	[PostalNotification] [int] NULL,
	[MinAge] [int] NULL,
	[MembersCount] [int] NULL,
	[MembersComputingDatetime] [datetime2](0) NULL,
	[LockId] [nchar](40) NULL,
	[LockDate] [datetime2](0) NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_profilegroups_IdProfileGroup] PRIMARY KEY CLUSTERED 
(
	[IdProfileGroup] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.profilegroups' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'profilegroups'
GO
/****** Object:  Table [dbo].[prehomeimages]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[prehomeimages](
	[IdPreHomeImage] [int] IDENTITY(17,1) NOT NULL,
	[Active] [int] NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_prehomeimages_IdPreHomeImage] PRIMARY KEY CLUSTERED 
(
	[IdPreHomeImage] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.prehomeimages' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'prehomeimages'
GO
/****** Object:  Table [dbo].[permissions]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[permissions](
	[idPermission] [int] IDENTITY(14,1) NOT NULL,
	[IdRole] [int] NOT NULL,
	[IdModule] [int] NOT NULL,
	[Read] [smallint] NULL,
	[Create] [smallint] NULL,
	[Update] [smallint] NULL,
	[Delete] [smallint] NULL,
 CONSTRAINT [PK_permissions_idPermission] PRIMARY KEY CLUSTERED 
(
	[idPermission] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [permissions$idPermission_UNIQUE] UNIQUE NONCLUSTERED 
(
	[idPermission] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.permissions' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'permissions'
GO
/****** Object:  Table [dbo].[modules]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[modules](
	[idModule] [int] IDENTITY(14,1) NOT NULL,
	[Name] [nvarchar](45) NOT NULL,
 CONSTRAINT [PK_modules_idModule] PRIMARY KEY CLUSTERED 
(
	[idModule] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [modules$idModules_UNIQUE] UNIQUE NONCLUSTERED 
(
	[idModule] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [modules$Name_UNIQUE] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.modules' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'modules'
GO
/****** Object:  Table [dbo].[lookbookitems]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lookbookitems](
	[IdLookBookItem] [int] IDENTITY(699,1) NOT NULL,
	[Name] [nvarchar](255) NULL,
	[Description] [nvarchar](800) NULL,
	[LotId] [nvarchar](100) NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_lookbookitems_IdLookBookItem] PRIMARY KEY CLUSTERED 
(
	[IdLookBookItem] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.lookbookitems' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lookbookitems'
GO
/****** Object:  Table [dbo].[devicetypes]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[devicetypes](
	[IdDeviceType] [int] IDENTITY(3,1) NOT NULL,
	[Code] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_devicetypes_IdDeviceType] PRIMARY KEY CLUSTERED 
(
	[IdDeviceType] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [devicetypes$Code_UNIQUE] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.devicetypes' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'devicetypes'
GO
/****** Object:  Table [dbo].[deviceos]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[deviceos](
	[IdDeviceOs] [int] IDENTITY(3,1) NOT NULL,
	[Code] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_deviceos_IdDeviceOs] PRIMARY KEY CLUSTERED 
(
	[IdDeviceOs] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [deviceos$Code_UNIQUE] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.deviceos' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'deviceos'
GO
/****** Object:  Table [dbo].[homeoffers]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[homeoffers](
	[IdHomeOffer] [int] IDENTITY(8,1) NOT NULL,
	[TargetURL] [nvarchar](255) NULL,
	[Active] [int] NULL,
	[StartDatetime] [datetime2](0) NULL,
	[ExpirationDatetime] [datetime2](0) NULL,
	[ItemOrder] [int] NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_homeoffers_IdHomeOffer] PRIMARY KEY CLUSTERED 
(
	[IdHomeOffer] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.homeoffers' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'homeoffers'
GO
/****** Object:  Table [dbo].[lookbookattributecategories]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lookbookattributecategories](
	[IdLookBookAttributeCategory] [int] IDENTITY(5,1) NOT NULL,
	[Code] [nvarchar](40) NOT NULL,
	[Name] [nvarchar](100) NULL,
	[ItemOrder] [int] NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_lookbookattributecategories_IdLookBookAttributeCategory] PRIMARY KEY CLUSTERED 
(
	[IdLookBookAttributeCategory] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [lookbookattributecategories$Code_UNIQUE] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.lookbookattributecategories' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lookbookattributecategories'
GO
/****** Object:  Table [dbo].[states]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[states](
	[IdState] [nchar](2) NOT NULL,
	[Name] [nvarchar](255) NULL,
 CONSTRAINT [PK_states_IdState] PRIMARY KEY CLUSTERED 
(
	[IdState] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.states' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'states'
GO
/****** Object:  Table [dbo].[socialupdates]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[socialupdates](
	[IdSocialUpdate] [int] IDENTITY(2325,1) NOT NULL,
	[IdSourceItem] [nchar](32) NOT NULL,
	[Name] [nvarchar](800) NULL,
	[Text] [nvarchar](800) NULL,
	[ImageURL] [nvarchar](255) NULL,
	[TargetURL] [nvarchar](255) NULL,
	[Source] [nchar](2) NOT NULL,
	[SocialItemDatetime] [datetime2](0) NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_socialupdates_IdSocialUpdate] PRIMARY KEY CLUSTERED 
(
	[IdSocialUpdate] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [socialupdates$ItemId_source_UQ] UNIQUE NONCLUSTERED 
(
	[IdSourceItem] ASC,
	[Source] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.socialupdates' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'socialupdates'
GO
/****** Object:  Table [dbo].[socialfeedaccesslog]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[socialfeedaccesslog](
	[IdSocialFeedAccessLog] [int] IDENTITY(3,1) NOT NULL,
	[Source] [nvarchar](255) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_socialfeedaccesslog_IdSocialFeedAccessLog] PRIMARY KEY CLUSTERED 
(
	[IdSocialFeedAccessLog] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.socialfeedaccesslog' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'socialfeedaccesslog'
GO
/****** Object:  Table [dbo].[roles]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[idRole] [int] IDENTITY(2,1) NOT NULL,
	[Name] [nvarchar](45) NOT NULL,
	[Rolescol] [nvarchar](45) NULL,
 CONSTRAINT [PK_roles_idRole] PRIMARY KEY CLUSTERED 
(
	[idRole] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [roles$idRole_UNIQUE] UNIQUE NONCLUSTERED 
(
	[idRole] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.roles' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'roles'
GO
/****** Object:  Table [dbo].[users]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[IdUser] [int] IDENTITY(3,1) NOT NULL,
	[Email] [nvarchar](45) NOT NULL,
	[Password] [nvarchar](255) NOT NULL,
	[Name] [nvarchar](255) NULL,
	[IdRole] [int] NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_users_IdUser] PRIMARY KEY CLUSTERED 
(
	[IdUser] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [users$Email_UNIQUE] UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [users$idUsers_UNIQUE] UNIQUE NONCLUSTERED 
(
	[IdUser] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.users' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'users'
GO
/****** Object:  Table [dbo].[stores]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[stores](
	[IdStore] [int] NOT NULL,
	[Name] [nvarchar](255) NULL,
	[Address] [nvarchar](255) NULL,
	[ContactNumber] [nvarchar](40) NULL,
	[Latitude] [float] NULL,
	[Longitude] [float] NULL,
	[ZipCode] [nvarchar](10) NULL,
	[City] [nvarchar](100) NULL,
	[IdState] [nchar](2) NOT NULL,
	[StoreHours] [nvarchar](max) NULL,
	[GeolocationProcessingDatetime] [datetime2](0) NULL,
	[GeolocationTries] [int] NOT NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_stores_IdStore] PRIMARY KEY CLUSTERED 
(
	[IdStore] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [fk_Stores_States1_idx] ON [dbo].[stores] 
(
	[IdState] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [ix_geolocationprocessing] ON [dbo].[stores] 
(
	[GeolocationProcessingDatetime] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.stores' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'stores'
GO
/****** Object:  Table [dbo].[lookbookattributes]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lookbookattributes](
	[IdLookBookAttribute] [int] IDENTITY(4040,1) NOT NULL,
	[IdLookBookAttributeCategory] [int] NOT NULL,
	[Name] [nvarchar](255) NULL,
	[ItemOrder] [int] NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_lookbookattributes_IdLookBookAttribute] PRIMARY KEY CLUSTERED 
(
	[IdLookBookAttribute] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [fk_LookBookFilters_LookBookFiltersCategories1_idx] ON [dbo].[lookbookattributes] 
(
	[IdLookBookAttributeCategory] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.lookbookattributes' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lookbookattributes'
GO
/****** Object:  Table [dbo].[profiles]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[profiles](
	[IdProfile] [int] IDENTITY(181637,1) NOT NULL,
	[DeviceId] [nchar](40) NOT NULL,
	[IdDeviceType] [int] NOT NULL,
	[IdDeviceOs] [int] NOT NULL,
	[AppVersion] [nvarchar](40) NOT NULL,
	[ResolutionWidth] [int] NULL,
	[ResolutionHeight] [int] NULL,
	[CompletedRegistration] [int] NULL,
	[Latitude] [float] NULL,
	[Longitude] [float] NULL,
	[Geolocation] [int] NULL,
	[PushNotification] [int] NULL,
	[SMSNotification] [int] NULL,
	[Xid] [nvarchar](40) NULL,
	[EmailNotification] [int] NULL,
	[PostalNotification] [int] NULL,
	[FirstName] [nvarchar](255) NULL,
	[LastName] [nvarchar](255) NULL,
	[Email] [nvarchar](255) NULL,
	[BirthDate] [date] NULL,
	[Gender] [nvarchar](45) NULL,
	[Address_1] [nvarchar](255) NULL,
	[Address_2] [nvarchar](255) NULL,
	[City] [nvarchar](255) NULL,
	[IdState] [nchar](2) NULL,
	[ZipCode] [nvarchar](5) NULL,
	[PhoneNumber] [nvarchar](20) NULL,
	[LastAppUseDatetime] [datetime2](0) NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_profiles_IdProfile] PRIMARY KEY CLUSTERED 
(
	[IdProfile] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [profiles$DeviceId_UNIQUE] UNIQUE NONCLUSTERED 
(
	[DeviceId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [fk_idstate_idx] ON [dbo].[profiles] 
(
	[IdState] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [fk_Profiles_DeviceOs1_idx] ON [dbo].[profiles] 
(
	[IdDeviceOs] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [fk_Profiles_DeviceTypes1_idx] ON [dbo].[profiles] 
(
	[IdDeviceType] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.profiles' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'profiles'
GO
/****** Object:  Table [dbo].[imagesizes]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imagesizes](
	[IdImageSize] [int] IDENTITY(4,1) NOT NULL,
	[Name] [nvarchar](45) NULL,
	[IdDeviceOs] [int] NOT NULL,
	[Width] [int] NULL,
	[Height] [int] NULL,
 CONSTRAINT [PK_imagesizes_IdImageSize] PRIMARY KEY CLUSTERED 
(
	[IdImageSize] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [ix_IdDeviceOs_idx] ON [dbo].[imagesizes] 
(
	[IdDeviceOs] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.imagesizes' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'imagesizes'
GO
/****** Object:  Table [dbo].[enabledsections]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[enabledsections](
	[IdEnabledSection] [int] IDENTITY(37,1) NOT NULL,
	[Section] [varchar](45) NULL,
	[IdDeviceOs] [int] NULL,
	[IsEnabled] [int] NULL,
 CONSTRAINT [PK_enabledsections_IdEnabledSection] PRIMARY KEY CLUSTERED 
(
	[IdEnabledSection] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [ix_es_IdDeviceOs_idx] ON [dbo].[enabledsections] 
(
	[IdDeviceOs] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.enabledsections' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'enabledsections'
GO
/****** Object:  Table [dbo].[coupons]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[coupons](
	[IdCoupon] [int] IDENTITY(8,1) NOT NULL,
	[Title] [nvarchar](100) NULL,
	[PromoCode] [nvarchar](50) NULL,
	[TargetURL] [nvarchar](255) NULL,
	[Active] [int] NULL,
	[StartDatetime] [datetime2](0) NULL,
	[ExpirationDatetime] [datetime2](0) NULL,
	[ItemOrder] [int] NULL,
	[IdProfileGroup] [int] NULL,
	[CreationDatetime] [datetime2](0) NULL,
	[ModificationDatetime] [datetime2](0) NULL,
	[DeletionDatetime] [datetime2](0) NULL,
 CONSTRAINT [PK_coupons_IdCoupon] PRIMARY KEY CLUSTERED 
(
	[IdCoupon] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [ix_coupons_idprofile_group] ON [dbo].[coupons] 
(
	[IdProfileGroup] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.coupons' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'coupons'
GO
/****** Object:  Table [dbo].[lookbookitemattributes]    Script Date: 10/13/2014 23:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lookbookitemattributes](
	[idLookBookItemAttribute] [int] IDENTITY(291,1) NOT NULL,
	[IdLookBookItem] [int] NOT NULL,
	[IdLookBookAttribute] [int] NOT NULL,
 CONSTRAINT [PK_lookbookitemattributes_idLookBookItemAttribute] PRIMARY KEY CLUSTERED 
(
	[idLookBookItemAttribute] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [attrib_item_idx] ON [dbo].[lookbookitemattributes] 
(
	[IdLookBookAttribute] ASC,
	[IdLookBookItem] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [Item_attrib_idx] ON [dbo].[lookbookitemattributes] 
(
	[IdLookBookItem] ASC,
	[IdLookBookAttribute] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'cuponera.lookbookitemattributes' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'lookbookitemattributes'
GO
/****** Object:  Default [DF__profilegro__Name__3EDC53F0]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__profilegr__IsFix__3FD07829]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [IsFixed]
GO
/****** Object:  Default [DF__profilegr__AppVe__40C49C62]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [AppVersion]
GO
/****** Object:  Default [DF__profilegr__Geolo__41B8C09B]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [Geolocation]
GO
/****** Object:  Default [DF__profilegr__PushN__42ACE4D4]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [PushNotification]
GO
/****** Object:  Default [DF__profilegr__SMSNo__43A1090D]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [SMSNotification]
GO
/****** Object:  Default [DF__profilegr__Email__44952D46]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [EmailNotification]
GO
/****** Object:  Default [DF__profilegr__Posta__4589517F]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [PostalNotification]
GO
/****** Object:  Default [DF__profilegr__MinAg__467D75B8]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [MinAge]
GO
/****** Object:  Default [DF__profilegr__Membe__477199F1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [MembersCount]
GO
/****** Object:  Default [DF__profilegr__Membe__4865BE2A]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [MembersComputingDatetime]
GO
/****** Object:  Default [DF__profilegr__LockI__4959E263]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [LockId]
GO
/****** Object:  Default [DF__profilegr__LockD__4A4E069C]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [LockDate]
GO
/****** Object:  Default [DF__profilegr__Creat__4B422AD5]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__profilegr__Modif__4C364F0E]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__profilegr__Delet__4D2A7347]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profilegroups] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__prehomeim__Activ__373B3228]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[prehomeimages] ADD  DEFAULT (NULL) FOR [Active]
GO
/****** Object:  Default [DF__prehomeim__Creat__382F5661]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[prehomeimages] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__prehomeim__Modif__39237A9A]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[prehomeimages] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__prehomeim__Delet__3A179ED3]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[prehomeimages] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__permission__Read__32767D0B]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[permissions] ADD  DEFAULT ((0)) FOR [Read]
GO
/****** Object:  Default [DF__permissio__Creat__336AA144]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[permissions] ADD  DEFAULT ((0)) FOR [Create]
GO
/****** Object:  Default [DF__permissio__Updat__345EC57D]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[permissions] ADD  DEFAULT ((0)) FOR [Update]
GO
/****** Object:  Default [DF__permissio__Delet__3552E9B6]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[permissions] ADD  DEFAULT ((0)) FOR [Delete]
GO
/****** Object:  Default [DF__lookbookit__Name__27F8EE98]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitems] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__lookbooki__Descr__28ED12D1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitems] ADD  DEFAULT (NULL) FOR [Description]
GO
/****** Object:  Default [DF__lookbooki__LotId__29E1370A]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitems] ADD  DEFAULT (NULL) FOR [LotId]
GO
/****** Object:  Default [DF__lookbooki__Creat__2AD55B43]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitems] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__lookbooki__Modif__2BC97F7C]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitems] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__lookbooki__Delet__2CBDA3B5]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitems] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__homeoffer__Targe__0F2D40CE]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT (NULL) FOR [TargetURL]
GO
/****** Object:  Default [DF__homeoffer__Activ__10216507]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT (NULL) FOR [Active]
GO
/****** Object:  Default [DF__homeoffer__Start__11158940]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT (NULL) FOR [StartDatetime]
GO
/****** Object:  Default [DF__homeoffer__Expir__1209AD79]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT (NULL) FOR [ExpirationDatetime]
GO
/****** Object:  Default [DF__homeoffer__ItemO__12FDD1B2]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT ((0)) FOR [ItemOrder]
GO
/****** Object:  Default [DF__homeoffer__Creat__13F1F5EB]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__homeoffer__Modif__14E61A24]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__homeoffer__Delet__15DA3E5D]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[homeoffers] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__lookbookat__Name__1B9317B3]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributecategories] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__lookbooka__ItemO__1C873BEC]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributecategories] ADD  DEFAULT (NULL) FOR [ItemOrder]
GO
/****** Object:  Default [DF__lookbooka__Creat__1D7B6025]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributecategories] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__lookbooka__Modif__1E6F845E]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributecategories] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__lookbooka__Delet__1F63A897]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributecategories] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__states__Name__00AA174D]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[states] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__socialupda__Name__7073AF84]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__socialupda__Text__7167D3BD]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [Text]
GO
/****** Object:  Default [DF__socialupd__Image__725BF7F6]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [ImageURL]
GO
/****** Object:  Default [DF__socialupd__Targe__73501C2F]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [TargetURL]
GO
/****** Object:  Default [DF__socialupd__Socia__74444068]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [SocialItemDatetime]
GO
/****** Object:  Default [DF__socialupd__Creat__753864A1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__socialupd__Modif__762C88DA]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__socialupd__Delet__7720AD13]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialupdates] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__socialfee__Sourc__6D9742D9]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialfeedaccesslog] ADD  DEFAULT (NULL) FOR [Source]
GO
/****** Object:  Default [DF__socialfee__Modif__6E8B6712]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[socialfeedaccesslog] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__roles__Rolescol__6BAEFA67]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[roles] ADD  DEFAULT (NULL) FOR [Rolescol]
GO
/****** Object:  Default [DF__users__Name__11D4A34F]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[users] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__users__IdRole__12C8C788]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[users] ADD  DEFAULT (NULL) FOR [IdRole]
GO
/****** Object:  Default [DF__users__CreationD__13BCEBC1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[users] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__users__Modificat__14B10FFA]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[users] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__users__DeletionD__15A53433]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[users] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__stores__Name__02925FBF]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__stores__Address__038683F8]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [Address]
GO
/****** Object:  Default [DF__stores__ContactN__047AA831]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [ContactNumber]
GO
/****** Object:  Default [DF__stores__Latitude__056ECC6A]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [Latitude]
GO
/****** Object:  Default [DF__stores__Longitud__0662F0A3]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [Longitude]
GO
/****** Object:  Default [DF__stores__ZipCode__075714DC]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [ZipCode]
GO
/****** Object:  Default [DF__stores__City__084B3915]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [City]
GO
/****** Object:  Default [DF__stores__Geolocat__093F5D4E]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [GeolocationProcessingDatetime]
GO
/****** Object:  Default [DF__stores__Geolocat__0A338187]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT ((0)) FOR [GeolocationTries]
GO
/****** Object:  Default [DF__stores__Creation__0B27A5C0]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__stores__Modifica__0C1BC9F9]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__stores__Deletion__0D0FEE32]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__lookbookat__Name__214BF109]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributes] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__lookbooka__ItemO__22401542]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributes] ADD  DEFAULT ((0)) FOR [ItemOrder]
GO
/****** Object:  Default [DF__lookbooka__Creat__2334397B]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributes] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__lookbooka__Modif__24285DB4]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributes] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__lookbooka__Delet__251C81ED]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributes] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__profiles__Resolu__50FB042B]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [ResolutionWidth]
GO
/****** Object:  Default [DF__profiles__Resolu__51EF2864]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [ResolutionHeight]
GO
/****** Object:  Default [DF__profiles__Comple__52E34C9D]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT ((0)) FOR [CompletedRegistration]
GO
/****** Object:  Default [DF__profiles__Latitu__53D770D6]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [Latitude]
GO
/****** Object:  Default [DF__profiles__Longit__54CB950F]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [Longitude]
GO
/****** Object:  Default [DF__profiles__Geoloc__55BFB948]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT ((0)) FOR [Geolocation]
GO
/****** Object:  Default [DF__profiles__PushNo__56B3DD81]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT ((0)) FOR [PushNotification]
GO
/****** Object:  Default [DF__profiles__SMSNot__57A801BA]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT ((0)) FOR [SMSNotification]
GO
/****** Object:  Default [DF__profiles__Xid__589C25F3]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [Xid]
GO
/****** Object:  Default [DF__profiles__EmailN__59904A2C]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT ((0)) FOR [EmailNotification]
GO
/****** Object:  Default [DF__profiles__Postal__5A846E65]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT ((0)) FOR [PostalNotification]
GO
/****** Object:  Default [DF__profiles__FirstN__5B78929E]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [FirstName]
GO
/****** Object:  Default [DF__profiles__LastNa__5C6CB6D7]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [LastName]
GO
/****** Object:  Default [DF__profiles__Email__5D60DB10]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [Email]
GO
/****** Object:  Default [DF__profiles__BirthD__5E54FF49]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [BirthDate]
GO
/****** Object:  Default [DF__profiles__Gender__5F492382]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [Gender]
GO
/****** Object:  Default [DF__profiles__Addres__603D47BB]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [Address_1]
GO
/****** Object:  Default [DF__profiles__Addres__61316BF4]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [Address_2]
GO
/****** Object:  Default [DF__profiles__City__6225902D]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [City]
GO
/****** Object:  Default [DF__profiles__IdStat__6319B466]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [IdState]
GO
/****** Object:  Default [DF__profiles__ZipCod__640DD89F]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [ZipCode]
GO
/****** Object:  Default [DF__profiles__PhoneN__6501FCD8]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [PhoneNumber]
GO
/****** Object:  Default [DF__profiles__LastAp__65F62111]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [LastAppUseDatetime]
GO
/****** Object:  Default [DF__profiles__Creati__66EA454A]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__profiles__Modifi__67DE6983]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__profiles__Deleti__68D28DBC]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  Default [DF__imagesizes__Name__17C286CF]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[imagesizes] ADD  DEFAULT (NULL) FOR [Name]
GO
/****** Object:  Default [DF__imagesize__Width__18B6AB08]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[imagesizes] ADD  DEFAULT (NULL) FOR [Width]
GO
/****** Object:  Default [DF__imagesize__Heigh__19AACF41]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[imagesizes] ADD  DEFAULT (NULL) FOR [Height]
GO
/****** Object:  Default [DF__enabledse__Secti__0880433F]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[enabledsections] ADD  DEFAULT (NULL) FOR [Section]
GO
/****** Object:  Default [DF__enabledse__IdDev__09746778]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[enabledsections] ADD  DEFAULT (NULL) FOR [IdDeviceOs]
GO
/****** Object:  Default [DF__enabledse__IsEna__0A688BB1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[enabledsections] ADD  DEFAULT (NULL) FOR [IsEnabled]
GO
/****** Object:  Default [DF__coupons__Title__7B264821]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [Title]
GO
/****** Object:  Default [DF__coupons__PromoCo__7C1A6C5A]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [PromoCode]
GO
/****** Object:  Default [DF__coupons__TargetU__7D0E9093]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [TargetURL]
GO
/****** Object:  Default [DF__coupons__Active__7E02B4CC]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [Active]
GO
/****** Object:  Default [DF__coupons__StartDa__7EF6D905]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [StartDatetime]
GO
/****** Object:  Default [DF__coupons__Expirat__7FEAFD3E]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [ExpirationDatetime]
GO
/****** Object:  Default [DF__coupons__ItemOrd__00DF2177]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT ((0)) FOR [ItemOrder]
GO
/****** Object:  Default [DF__coupons__IdProfi__01D345B0]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [IdProfileGroup]
GO
/****** Object:  Default [DF__coupons__Creatio__02C769E9]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [CreationDatetime]
GO
/****** Object:  Default [DF__coupons__Modific__03BB8E22]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [ModificationDatetime]
GO
/****** Object:  Default [DF__coupons__Deletio__04AFB25B]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons] ADD  DEFAULT (NULL) FOR [DeletionDatetime]
GO
/****** Object:  ForeignKey [stores$IdState]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[stores]  WITH CHECK ADD  CONSTRAINT [stores$IdState] FOREIGN KEY([IdState])
REFERENCES [dbo].[states] ([IdState])
GO
ALTER TABLE [dbo].[stores] CHECK CONSTRAINT [stores$IdState]
GO
/****** Object:  ForeignKey [lookbookattributes$fk_LookBookFilters_LookBookFiltersCategories1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookattributes]  WITH CHECK ADD  CONSTRAINT [lookbookattributes$fk_LookBookFilters_LookBookFiltersCategories1] FOREIGN KEY([IdLookBookAttributeCategory])
REFERENCES [dbo].[lookbookattributecategories] ([IdLookBookAttributeCategory])
GO
ALTER TABLE [dbo].[lookbookattributes] CHECK CONSTRAINT [lookbookattributes$fk_LookBookFilters_LookBookFiltersCategories1]
GO
/****** Object:  ForeignKey [profiles$fk_idstate]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles]  WITH CHECK ADD  CONSTRAINT [profiles$fk_idstate] FOREIGN KEY([IdState])
REFERENCES [dbo].[states] ([IdState])
GO
ALTER TABLE [dbo].[profiles] CHECK CONSTRAINT [profiles$fk_idstate]
GO
/****** Object:  ForeignKey [profiles$fk_Profiles_DeviceOs1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles]  WITH CHECK ADD  CONSTRAINT [profiles$fk_Profiles_DeviceOs1] FOREIGN KEY([IdDeviceOs])
REFERENCES [dbo].[deviceos] ([IdDeviceOs])
GO
ALTER TABLE [dbo].[profiles] CHECK CONSTRAINT [profiles$fk_Profiles_DeviceOs1]
GO
/****** Object:  ForeignKey [profiles$fk_Profiles_DeviceTypes1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[profiles]  WITH CHECK ADD  CONSTRAINT [profiles$fk_Profiles_DeviceTypes1] FOREIGN KEY([IdDeviceType])
REFERENCES [dbo].[devicetypes] ([IdDeviceType])
GO
ALTER TABLE [dbo].[profiles] CHECK CONSTRAINT [profiles$fk_Profiles_DeviceTypes1]
GO
/****** Object:  ForeignKey [imagesizes$ix_IdDeviceOs]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[imagesizes]  WITH CHECK ADD  CONSTRAINT [imagesizes$ix_IdDeviceOs] FOREIGN KEY([IdDeviceOs])
REFERENCES [dbo].[deviceos] ([IdDeviceOs])
GO
ALTER TABLE [dbo].[imagesizes] CHECK CONSTRAINT [imagesizes$ix_IdDeviceOs]
GO
/****** Object:  ForeignKey [enabledsections$ix_es_IdDeviceOs]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[enabledsections]  WITH CHECK ADD  CONSTRAINT [enabledsections$ix_es_IdDeviceOs] FOREIGN KEY([IdDeviceOs])
REFERENCES [dbo].[deviceos] ([IdDeviceOs])
GO
ALTER TABLE [dbo].[enabledsections] CHECK CONSTRAINT [enabledsections$ix_es_IdDeviceOs]
GO
/****** Object:  ForeignKey [coupons$fk_ix_coupons_profilegroupid]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[coupons]  WITH CHECK ADD  CONSTRAINT [coupons$fk_ix_coupons_profilegroupid] FOREIGN KEY([IdProfileGroup])
REFERENCES [dbo].[profilegroups] ([IdProfileGroup])
GO
ALTER TABLE [dbo].[coupons] CHECK CONSTRAINT [coupons$fk_ix_coupons_profilegroupid]
GO
/****** Object:  ForeignKey [lookbookitemattributes$fk_LookBookItemAttributes_LookBookAttributes1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitemattributes]  WITH CHECK ADD  CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookAttributes1] FOREIGN KEY([IdLookBookAttribute])
REFERENCES [dbo].[lookbookattributes] ([IdLookBookAttribute])
GO
ALTER TABLE [dbo].[lookbookitemattributes] CHECK CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookAttributes1]
GO
/****** Object:  ForeignKey [lookbookitemattributes$fk_LookBookItemAttributes_LookBookItems1]    Script Date: 10/13/2014 23:09:24 ******/
ALTER TABLE [dbo].[lookbookitemattributes]  WITH CHECK ADD  CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookItems1] FOREIGN KEY([IdLookBookItem])
REFERENCES [dbo].[lookbookitems] ([IdLookBookItem])
GO
ALTER TABLE [dbo].[lookbookitemattributes] CHECK CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookItems1]
GO
