package it.polito.applied.asti.clan.pojo;

public enum LogType {
	OpenApp, OpenPath, ClosePath, 
	DownloadFinished, UnzipFinished, OpenPoi, ClosePoi,
	OpenAudio, CloseAudio, OpenPanoImage, ClosePanoImage,
	OpenVideo, CloseVideo, DirectionRequest,
	OpenGalleryItem, CloseGalleryItem,
	CheckTicketFEC, CheckTicket,
	DeletePath, Comment
}
